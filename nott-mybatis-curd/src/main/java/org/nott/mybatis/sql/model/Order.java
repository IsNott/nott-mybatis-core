package org.nott.mybatis.sql.model;

import lombok.Getter;
import lombok.Setter;
import org.nott.mybatis.sql.enums.OrderMode;

/**
 * @author Nott
 * @date 2024-5-15
 */
@Setter
@Getter
public class Order {

    private String colum;

    private OrderMode orderMode;
}
