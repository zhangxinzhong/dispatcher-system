package com.yunnan.tools.execl.support;

import com.yunnan.tools.execl.Excel;
import com.yunnan.tools.execl.ExcelColumn;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

/**
 * excel support
 *
 * @author: <a href="mailto:zhangxz_email@163.com">zxz</a>
 * @create: 2023/10/16 18:23
 * @since: 1.0.0
 **/
@Component
public class ExcelSupport {

    public <T> List<T> parse(Sheet sheet, Class<?> pojoClass) throws Exception {
        List<T> list = new ArrayList<T>();
        Excel excel = pojoClass.getAnnotation(Excel.class);
        if (excel == null) {
            return list;
        }

        Map<String, Method> allExcelMethods = getAllExcelMethods(pojoClass);
        Iterator<Row> rowIterator = sheet.rowIterator();
        Map<Integer, String> titleMap = getTitleInfo(rowIterator, excel.titleRowNo());
        Row row;
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            if (row == null) {
                break;
            }
            T obj = (T) pojoClass.newInstance();
            Iterator<Cell> cellIterator = row.iterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell == null) {
                    break;
                }
                int idx = cell.getColumnIndex();
                String title = titleMap.get(idx);
                if (title == null) {
                    continue;
                }
                Method method = allExcelMethods.get(title);
                if (method == null) {
                    continue;
                }
                setFieldValue(obj, method, cell);
            }
            list.add(obj);
        }


        return list;
    }

    /**
     * 从Excel单元格取值，并为对象设置属性
     *
     * @param obj
     * @param method
     * @param cell
     * @author qfxu
     */
    private void setFieldValue(Object obj, Method method, Cell cell) throws Exception {
        if (obj == null || method == null || cell == null) {
            throw new NullPointerException();
        }
        Type[] ts = method.getGenericParameterTypes();
        // 得到set方法的参数
        String xclass = ts[0].toString();
        // 判断参数类型
        if (xclass.equals("class java.lang.String")) {
            // 先设置Cell的类型，然后就可以把纯数字作为String类型读进来了：
            cell.setCellType(Cell.CELL_TYPE_STRING);
            method.invoke(obj, cell.getStringCellValue().trim());
        } else if (xclass.equals("class java.util.Date")) {
            method.invoke(obj, cell.getDateCellValue());
        } else if (xclass.equals("class java.lang.Boolean")) {
            method.invoke(obj, cell.getBooleanCellValue());
        } else if (xclass.equals("class java.lang.Integer")) {
            method.invoke(obj, new Integer(cell.getStringCellValue()));
        } else if (xclass.equals("class java.lang.Long")) {
            method.invoke(obj, new Long(cell.getStringCellValue()));
        }
    }

    private Map<String, Method> getAllExcelMethods(Class<?> pojoClass) {
        Map<String, Method> methodMap = new HashMap<String, Method>();
        Field[] fields = getClassFields(pojoClass);
        for (Field field : fields) {
            ExcelColumn column = field.getAnnotation(ExcelColumn.class);
            if (column == null) {
                continue;
            }
            String name = column.name();
            Method setMethod = getMethod(field, pojoClass);
            methodMap.put(name, setMethod);
        }
        return methodMap;
    }

    private Method getMethod(Field field, Class<?> pojoClass) {
        String fieldName = field.getName();
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method method = null;
        try {
            method = pojoClass.getMethod(methodName, new Class[]{field.getType()});
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(pojoClass.getName() + " not contain method：[" + methodName + "]");
        }
        return method;
    }

    private Field[] getClassFields(Class<?> pojoClass) {
        List<Field> list = new ArrayList<Field>();
        Field[] fields;
        do {
            fields = pojoClass.getDeclaredFields();
            for (Field field : fields) {
                list.add(field);
            }
            pojoClass = pojoClass.getSuperclass();
        } while (pojoClass != Object.class && pojoClass != null);
        return list.toArray(fields);
    }

    /**
     * 得到表格字段列民对应信息
     *
     * @param rowIterator
     * @param titleRow
     * @return
     */
    private Map<Integer, String> getTitleInfo(Iterator<Row> rowIterator, int titleRow) {
        Map<Integer, String> titleMap = new HashMap<Integer, String>();
        Row row = null;
        int j = 0;
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            if (++j == titleRow) {
                break;
            }
        }
        if (row == null) {
            return titleMap;
        }
        Iterator<Cell> iterator = row.cellIterator();
        while (iterator.hasNext()) {
            Cell cell = iterator.next();
            int idx = cell.getColumnIndex();
            String title = getCellValue(cell);
            if (title != null && !"".equals(title)) {
                titleMap.put(idx, title);
            }

        }
        return titleMap;
    }

    private String getCellValue(Cell cell) {
        Object obj = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                obj = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                obj = cell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                obj = cell.getCellFormula();
                break;
        }

        return obj == null ? null : obj.toString().trim();
    }
}
