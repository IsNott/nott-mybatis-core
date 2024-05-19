package org.nott.mybatis.sql;

import lombok.Data;
import org.nott.mybatis.sql.enums.LikeMode;
import org.nott.mybatis.sql.enums.OrderMode;
import org.nott.mybatis.sql.enums.SqlOperator;
import org.nott.mybatis.sql.interfaces.SqlQuery;
import org.nott.mybatis.sql.model.Colum;
import org.nott.mybatis.sql.model.InLike;
import org.nott.mybatis.sql.model.InSelect;
import org.nott.mybatis.sql.model.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class QuerySqlConditionBuilder extends Builder {

    public static QuerySqlConditionBuilder build() {
        return new QuerySqlConditionBuilder();
    }


}
