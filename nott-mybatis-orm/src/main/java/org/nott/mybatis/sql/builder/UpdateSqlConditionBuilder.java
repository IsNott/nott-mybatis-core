package org.nott.mybatis.sql.builder;

import lombok.Data;
import org.nott.mybatis.exception.MethodNotSupportException;
import org.nott.mybatis.sql.enums.SqlDDLOption;
import org.nott.mybatis.sql.model.InSelect;

/**
 * @author Nott
 * @date 2024-5-15
 */

@Data
public class UpdateSqlConditionBuilder extends Builder {

    private static final SqlDDLOption OPT = SqlDDLOption.UPDATE;

    public static UpdateSqlConditionBuilder build(){
        return new UpdateSqlConditionBuilder();
    }

    @Override
    public Builder select(InSelect... selects) {
        return super.methodNotSupport(OPT,"select");
    }

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
