package com.yunnan.dispatcher.web;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.yunnan.dispatcher.web.pojo.InTable;
import com.yunnan.tools.execl.Column;
import com.yunnan.tools.execl.ExcelUtils;
import com.yunnan.tools.execl.Tuple2;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

/**
 * web bootstrap class
 *
 * @author: <a href="mailto:zhangxz_email@163.com">zxz</a>
 * @create: 2023-10-24 21:13
 * @since: 1.0.0
 **/
@SpringBootApplication(scanBasePackages = "com")
public class WebApplication {

    static Set<Tuple2<Integer, Integer>> size = new HashSet<>();

    static {
        size.add(new Tuple2<>(1, 3));
        size.add(new Tuple2<>(3, 5));
        size.add(new Tuple2<>(5, 7));
        size.add(new Tuple2<>(7, 9));
        size.add(new Tuple2<>(9, 11));
        size.add(new Tuple2<>(11, 13));
        size.add(new Tuple2<>(13, 15));
        size.add(new Tuple2<>(15, 17));
        size.add(new Tuple2<>(17, 19));
        size.add(new Tuple2<>(19, 21));
    }

    @Autowired
    private ExcelUtils excelUtils;

    public static void main(String[] args) {
        try {
            SpringApplication.run(WebApplication.class, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void init() {
        try {
            readFile("D://package/data.xls");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readFile(String filePath) throws IOException {
        File file = new File(filePath);
        Tuple2<Workbook, List<InTable>> tuple2 = excelUtils.parse(Files.newInputStream(file.toPath()), InTable.class);
        List<InTable> parse = tuple2.getT2();
        Map<String, List<InTable>> collect = parse.stream().filter(InTable::nonNull).collect(Collectors.groupingBy(InTable::getName));
        List<Tuple2<String, List<InTable>>> data = new ArrayList<>();
        size.forEach(t -> {
            collect.forEach((k, v) -> {
                data.add(new Tuple2<>(t.toString(), v.stream().filter(c -> Double.parseDouble(c.getSize()) > t.getT1() && Double.parseDouble(c.getSize()) < t.getT2()).collect(Collectors.toList())));
            });
        });


        // 树种	数量	胸径/cm	蓄积


        List<Map<String, Object>> result = new ArrayList<>();
        data.forEach(e -> {
            List<InTable> v = e.getT2().stream().sorted().collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(v)) {
                InTable it = new InTable();
                it.setName(v.stream().map(InTable::getName).findFirst().orElse(""));
                it.setSize(e.getT1());
                it.setNumber(String.valueOf(v.size()));
                it.setArea(String.valueOf(v.stream().mapToDouble(c -> Double.parseDouble(c.getArea())).sum()));
                Map<String, Object> map = BeanUtil.beanToMap(it);
                result.add(map);
            }
        });
        List<Column> columns = getColumns();
        Workbook workbook = tuple2.getT1();
        excelUtils.append(workbook, "xxxxxxxx", columns, result);
        // 写入文件
        String newFilePath = "D://package/数据+检尺表.xls";
        try (FileOutputStream outputStream = new FileOutputStream(newFilePath)) {
            workbook.write(outputStream);
        }
        workbook.close();
    }

    private static List<Column> getColumns() {
        List<Column> columns = new ArrayList<>();
        Column column1 = new Column();
        column1.setTitle("树种");
        column1.setField("name");
        columns.add(column1);

        Column column2 = new Column();
        column2.setTitle("数量");
        column2.setField("number");
        columns.add(column2);


        Column column3 = new Column();
        column3.setTitle("胸径/cm");
        column3.setField("size");
        columns.add(column3);

        Column column4 = new Column();
        column4.setTitle("蓄积");
        column4.setField("area");
        columns.add(column4);
        return columns;
    }
}
