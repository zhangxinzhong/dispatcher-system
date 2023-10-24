package com.yunnan.tools.execl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel annotation
 *
 * @author: <a href="mailto:zhangxz_email@163.com">zxz</a>
 * @create: 2023/10/16 18:23
 * @since: 1.0.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Excel {

    //标题行号
    public int titleRowNo() default 1;

    //数据行号
    public int dataRowNo() default 2;
}
