package org.nott.web.provider;

import org.apache.ibatis.jdbc.SQL;

public class UserSelectProvider {

    public static String selectUserByEmail(final String email) {
        return new SQL() {{
            SELECT("*");
            FROM("user");
            WHERE("email like #{email}");
            LIMIT(10);
        }}.toString();
    }

    public static String selectUserByName(final String name,final String orderByColum){
        return new SQL(){{
            SELECT("*");
            FROM("user");
            WHERE("name like #{name}");
            ORDER_BY(orderByColum);
        }}.toString();
    }
}
