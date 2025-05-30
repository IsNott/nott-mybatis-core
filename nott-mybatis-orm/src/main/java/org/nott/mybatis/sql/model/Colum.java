package org.nott.mybatis.sql.model;

import lombok.Data;
import org.nott.mybatis.sql.interfaces.SFunction;
import org.nott.mybatis.support.aop.utils.BaseUtils;

@Data
public class Colum {

    private String fieldName;

    private String asName;

    public static Colum select(String name){
        return select(name,"");
    }


    public static Colum select(String name,String asName){
        Colum colum = new Colum();
        colum.setFieldName(name);
        colum.setAsName(asName);
        return colum;
    }

    public static <T, R> Colum select(String table, SFunction<T, R> function, String asName) {
        return select(BaseUtils.getColumName(table, function), asName);
    }

    public static <T, R> Colum select(String table, SFunction<T, R> function) {
        return select(BaseUtils.getColumName(table, function), BaseUtils.getColumName(function));
    }
}
