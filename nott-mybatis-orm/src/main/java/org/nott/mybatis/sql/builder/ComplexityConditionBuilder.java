package org.nott.mybatis.sql.builder;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO multiple sql condition
 * @author Nott
 * @date 2024-5-17
 */

@Setter
@Getter
public class ComplexityConditionBuilder extends QuerySqlConditionBuilder {

    private Class<?> aClass;

    public ComplexityConditionBuilder() {
    }

    public static ComplexityConditionBuilder build(Class<?> clazz) {
        return ComplexityConditionBuilder.build(clazz, null);
    }

    public static ComplexityConditionBuilder build(Class<?> clazz, String alias) {
        ComplexityConditionBuilder builder = new ComplexityConditionBuilder();
        builder.setAClass(clazz);
        builder.setAlias(alias);
        return builder;
    }





}
