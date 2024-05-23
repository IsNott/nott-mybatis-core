package org.nott.mybatis.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Nott
 * @date 2024-5-14
 */
@Data
@Builder
public class MybatisSqlBean {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段集合
     */
    private List<String> tableColums;

    /**
     * 主键
     */
    private Pk pk;

    /**
     * bean
     */
    private Object originalBean;

    /**
     * bean的Class
     */
    private Class<?> originalBeanClass;

}
