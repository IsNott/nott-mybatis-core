package org.nott.mybatis.annotations;

import lombok.Data;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface CustTableName {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";
}
