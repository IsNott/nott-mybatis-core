package org.nott.mybatis.annotations;

import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.*;

/**
 * 自定义实体表名注解
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustTableName {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";
}
