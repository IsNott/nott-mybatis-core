package org.nott.mybatis.sql.builder;

import org.nott.mybatis.sql.enums.SqlDDLOption;

public class DeleteSqlConditionBuilder extends Builder {

    public static DeleteSqlConditionBuilder build(){
        return new DeleteSqlConditionBuilder();
    }

    private static final SqlDDLOption OPT = SqlDDLOption.DELETE;

    @Override
    public Builder limit(Integer value) {
        return super.methodNotSupport(OPT,"limit");
    }

    @Override
    public Builder append(String sql) {
        return super.methodNotSupport(OPT,"append");
    }

    @Override
    public Builder orderByDesc(String... colum) {
        return super.methodNotSupport(OPT,"orderBy");
    }

    @Override
    public Builder orderByAsc(String... colum) {
        return super.methodNotSupport(OPT,"orderBy");
        }

    @Override
    public Builder groupBy(String... colum) {
        return super.methodNotSupport(OPT,"groupBy");
    }

    @Override
    public Builder having(String... sql) {
        return super.methodNotSupport(OPT,"having");
    }
}
