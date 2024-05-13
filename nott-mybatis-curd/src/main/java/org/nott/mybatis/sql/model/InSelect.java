package org.nott.mybatis.sql.model;

public class InSelect {

    public static InSelectColum colum(String colum) {
        Colum selectColum = new Colum();
        selectColum.setFieldName(colum);
        return new InSelectColum(selectColum);
    }


}
