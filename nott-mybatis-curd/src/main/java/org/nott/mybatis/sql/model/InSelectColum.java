package org.nott.mybatis.sql.model;

/**
 * @author Nott
 * @date 2024-5-13
 */
public class InSelectColum {

    public Colum colum;

    public InSelectColum(Colum colum) {
        this.colum = colum;
    }

    public Colum as(String asName){
        this.colum.setAsName(asName);
        return colum;
    }
}
