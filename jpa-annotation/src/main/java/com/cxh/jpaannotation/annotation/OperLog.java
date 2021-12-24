package com.cxh.jpaannotation.annotation;


import com.cxh.jpaannotation.constant.OperType;

import java.lang.annotation.*;

/**
 * @description 操作日志注解
 * @return
 **/
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {
    /**
     * 标题
     */
    String title() default "";
    /**
     * 操作类型
     */
    OperType operType() default OperType.SELECT;

}
