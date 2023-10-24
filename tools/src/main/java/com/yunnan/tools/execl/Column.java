package com.yunnan.tools.execl;

/**
 * excel colum
 *
 * @author: <a href="mailto:zhangxz_email@163.com">zxz</a>
 * @create: 2023/10/16 18:23
 * @since: 1.0.0
 **/
public class Column {
    private String title;
    private String field;
    private String dict;
    private String width;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDict() {
        return dict;
    }

    public void setDict(String dict) {
        this.dict = dict;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
