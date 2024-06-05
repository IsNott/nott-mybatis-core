package org.nott.mybatis.sql.model;

import lombok.Data;

@Data
public class Colum {

    private String fieldName;

    private String asName;

    public static Colum select(String name){
        return Colum.select(name,null);
    }


    public static Colum select(String name,String asName){
        Colum colum = new Colum();
        colum.setFieldName(name);
        colum.setAsName(asName);
        return colum;
    }
}
