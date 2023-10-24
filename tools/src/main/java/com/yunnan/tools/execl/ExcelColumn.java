package com.yunnan.tools.execl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel colum
 *
 * @author: <a href="mailto:zhangxz_email@163.com">zxz</a>
 * @create: 2023/10/16 18:23
 * @since: 1.0.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {

    //对应的excel列名
    public String name();

    //对应的excel列宽度，用于excel导出
    public int width() default 20;

    //是否可以导出
    public boolean isExport() default true;
}
