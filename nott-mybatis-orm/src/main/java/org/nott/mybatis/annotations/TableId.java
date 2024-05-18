package org.nott.mybatis.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 定义主键注解
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableId {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";
}
