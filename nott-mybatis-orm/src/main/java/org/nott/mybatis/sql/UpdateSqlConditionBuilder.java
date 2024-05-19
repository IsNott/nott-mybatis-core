package org.nott.mybatis.sql;

import lombok.Data;
import org.nott.mybatis.exception.MethodNotAllowException;
import org.nott.mybatis.sql.interfaces.SqlUpdate;
import org.nott.mybatis.sql.model.Colum;
import org.nott.mybatis.sql.model.InSelect;
import org.nott.mybatis.sql.model.UpdateCombination;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nott
 * @date 2024-5-15
 */

@Data
public class UpdateSqlConditionBuilder extends Builder {


    public static UpdateSqlConditionBuilder build(){
        return new UpdateSqlConditionBuilder();
    }

    @Override
    public QuerySqlConditionBuilder select(InSelect... selects) {
        throw new MethodNotAllowException("Update option not allow call select method.");
    }

    @Override
    public QuerySqlConditionBuilder limit(Integer value) {
        throw new MethodNotAllowException("Update option not allow call limit method.");
    }

    @Override
    public QuerySqlConditionBuilder append(String sql) {
        throw new MethodNotAllowException("Update option not allow call append method.");
    }

    @Override
    public QuerySqlConditionBuilder orderByDesc(String... colum) {
        throw new MethodNotAllowException("Update option not allow call order by method.");
    }

    @Override
    public QuerySqlConditionBuilder orderByAsc(String... colum) {
        throw new MethodNotAllowException("Update option not allow call order by method.");
    }

    @Override
    public QuerySqlConditionBuilder groupBy(String... colum) {
        throw new MethodNotAllowException("Update option not allow call group by method.");
    }

    @Override
    public QuerySqlConditionBuilder having(String... sql) {
        throw new MethodNotAllowException("Update option not allow call having method.");
    }
}
