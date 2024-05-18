package org.nott.mybatis.annotations;


import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.*;

/**
 * 自定义属性名称注解
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustFieldName {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

}
