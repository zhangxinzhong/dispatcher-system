package com.yunnan.tools.execl;

import com.yunnan.tools.execl.support.ExcelSupport;
import org.apache.commons.io.IOUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * excel utils
 *
 * @author: <a href="mailto:zhangxz_email@163.com">zxz</a>
 * @create: 2023/10/16 18:23
 * @since: 1.0.0
 **/
@Component
public class ExcelUtils {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource
    private ExcelSupport excelSupport;

    @Resource
    private PojoUtils pojoUtils;

    public SXSSFWorkbook createWorkbook(int rowAccessWindowSize) {
        SXSSFWorkbook workbook = new SXSSFWorkbook(rowAccessWindowSize);
        return workbook;
    }

    /**
     * 追加数据
     *
     * @param workbook
     * @param name
     * @param columns
     * @param datas
     */
    public void append(Workbook workbook, String infoMessage, String name, List<Column> columns, List<Map<String, Object>> datas) {
        int numberOfSheets = workbook.getNumberOfSheets();
        Sheet sheet;
        if (numberOfSheets == 0) {
            sheet = workbook.createSheet(name);
            sheet.setDefaultColumnWidth(24);
        } else {
            sheet = workbook.getSheetAt(0);
        }
        int lastRowNum = sheet.getLastRowNum();
        int colAt = 0;
        Row row;

        CellStyle headStyle = createHeadStyle(workbook);
        CellStyle cellStyle = createContentStyle(workbook);

        if (lastRowNum == 0) {//第一行输出提示信息和标题
            if (infoMessage != null && !"".equals(infoMessage)) {
                row = sheet.createRow(lastRowNum++);
                Cell cell = row.createCell(colAt++);
                cell.setCellValue(infoMessage);

            }
            row = sheet.createRow(lastRowNum++);
            colAt = 0;
            for (Column column : columns) {
                Cell cell = row.createCell(colAt++);
                cell.setCellStyle(headStyle);
                String title = column.getTitle();
                if (title != null && !title.equals("")) {
                    title = title.replaceAll("<[.[^<]]*>", "");
                    cell.setCellValue(title);
                }
            }
        } else {
            //从下一行开始输出
            lastRowNum = lastRowNum + 1;
        }

        if (datas == null) {
            return;
        }

        for (Map<String, Object> data : datas) {
            colAt = 0;
            row = sheet.createRow(lastRowNum++);
            for (Column column : columns) {
                Cell cell = row.createCell(colAt++);
                cell.setCellStyle(cellStyle);
                Object obj = data.get(column.getField());
                if (obj == null) {
                    cell.setCellValue("");
                    continue;
                }
                String value = obj.toString();
                cell.setCellValue(value);
            }
        }
    }

    /**
     * 追加数据
     *
     * @param workbook
     * @param name
     * @param columns
     * @param datas
     */
    public void append(Workbook workbook, String name, List<Column> columns, List<Map<String, Object>> datas) {
        append(workbook, null, name, columns, datas);
    }

    /**
     * 解析excel，返回对象列表
     *
     * @param inputStream
     * @param pojoClass
     * @param <T>
     * @return
     */
    public <T> List<T> parse(InputStream inputStream, Class<?> pojoClass) {
        try {
            if (!inputStream.markSupported()) {
                inputStream = new PushbackInputStream(inputStream, 8);
            }

            Workbook workbook = null;
            if (POIFSFileSystem.hasPOIFSHeader(inputStream)) {
                workbook = new HSSFWorkbook(inputStream);
            } else if (POIXMLDocument.hasOOXMLHeader(inputStream)) {
                workbook = new XSSFWorkbook(OPCPackage.open(inputStream));
            }
            if (workbook == null)
                throw new RuntimeException("fail to create workbook");

            Sheet sheet = workbook.getSheetAt(0);
            return excelSupport.parse(sheet, pojoClass);
        } catch (Exception ex) {
            logger.error("fail to parse excel", ex);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return null;
    }

    /**
     * 解析excel，返回对象列表
     *
     * @param file
     * @param pojoClass
     * @param <T>
     * @return
     */
    public <T> List<T> parse(File file, Class<?> pojoClass) {
        InputStream in = null;
        try {
            in = new FileInputStream(file);

            return parse(in, pojoClass);
        } catch (Exception ex) {
            logger.error("fail to parse excel", ex);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return null;
    }

    /**
     * 导出excel
     *
     * @param sheetName
     * @param cls
     * @param datas
     * @param out
     * @param <T>
     * @throws IOException
     */
    public <T> void export(String sheetName, Class<T> cls, List<T> datas, OutputStream out) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth(24);

        int rowAt = 0;
        Row row = sheet.createRow(rowAt++);

        List<String> headers = new ArrayList<String>();
        List<String> cols = new ArrayList<String>();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            ExcelColumn column = field.getAnnotation(ExcelColumn.class);
            if (column == null) {
                continue;
            }
            headers.add(column.name());
            cols.add(field.getName());
        }


        CellStyle headStyle = createHeadStyle(workbook);
        CellStyle cellStyle = createContentStyle(workbook);
        int colAt = 0;
        for (String header : headers) {
            Cell cell = row.createCell(colAt++);
            cell.setCellStyle(headStyle);
            if (header != null && !header.equals("")) {
                header = header.replaceAll("<[.[^<]]*>", "");
                cell.setCellValue(header);
            }
        }

        for (T data : datas) {
            colAt = 0;
            row = sheet.createRow(rowAt++);
            for (String col : cols) {
                Cell cell = row.createCell(colAt++);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(pojoUtils.getField(data, col));
            }
        }


        workbook.write(out);
    }

    /**
     * 导出excel
     *
     * @param name
     * @param headers
     * @param cols
     * @param datas
     * @param out
     * @throws Exception
     */
    public void export(String name, List<String> headers, List<String> cols, List<Map<String, Object>> datas, OutputStream out) throws Exception {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(name);
        sheet.setDefaultColumnWidth(24);

        int rowAt = 0;
        Row row = sheet.createRow(rowAt++);

        int colAt = 0;
        CellStyle headStyle = createHeadStyle(workbook);
        CellStyle cellStyle = createContentStyle(workbook);
        for (String header : headers) {
            Cell cell = row.createCell(colAt++);
            cell.setCellStyle(headStyle);
            if (header != null && !header.equals("")) {
                header = header.replaceAll("<[.[^<]]*>", "");
                cell.setCellValue(header);
            }
        }

        for (Map<String, Object> data : datas) {
            colAt = 0;
            row = sheet.createRow(rowAt++);
            for (String col : cols) {
                Cell cell = row.createCell(colAt++);
                cell.setCellStyle(cellStyle);
                Object obj = data.get(col);
                if (obj != null) {
                    cell.setCellValue(data.get(col).toString());
                } else {
                    cell.setCellValue("");
                }
            }
        }


        workbook.write(out);
    }

    /**
     * 设置表格标题样式
     *
     * @param workbook
     * @return
     */
    private CellStyle createHeadStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        // 生成字体
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);

        // 把字体应用到当前的样样式
        style.setFont(font);

        //设置文本格式
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("@"));

        return style;
    }

    /**
     * 设置正文样式
     *
     * @param workbook
     * @return
     */
    private CellStyle createContentStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillBackgroundColor(HSSFColor.WHITE.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        // 生成字体
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);

        // 把字体应用到当前的样样式
        style.setFont(font);

        //设置文本格式
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("@"));
        return style;
    }

}
