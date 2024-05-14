package org.nott.mybatis.sql.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InSelect {

    Colum colum;

    public static InSelect colum(String colum) {
        InSelect select = new InSelect();
        Colum selectColum = new Colum();
        selectColum.setFieldName(colum);
        select.setColum(selectColum);
        return select;
    }

    public InSelect as(String asName){
        this.colum.setAsName(asName);
        return this;
    }


}
