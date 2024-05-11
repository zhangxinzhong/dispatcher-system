package com.yunnan.dispatcher.web.pojo;

import com.yunnan.tools.execl.Excel;
import com.yunnan.tools.execl.ExcelColumn;

import java.util.Objects;

@Excel(dataRowNo = 2)
public class InTable implements Comparable<InTable> {

    @ExcelColumn(name = "树种")
    private String name;

    @ExcelColumn(name = "数量")
    private String number;

    @ExcelColumn(name = "胸径/cm")
    private String size;

    @ExcelColumn(name = "蓄积")
    private String area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean nonNull() {
        return Objects.nonNull(this) && Objects.nonNull(this.name) && Objects.nonNull(this.area) && Objects.nonNull(this.number) && Objects.nonNull(this.size);
    }

    @Override
    public String toString() {
        return "InTable{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", size='" + size + '\'' +
                ", area='" + area + '\'' +
                '}';
    }

    @Override
    public int compareTo(InTable o) {
        return this.name.compareTo(o.getName());
    }
}
