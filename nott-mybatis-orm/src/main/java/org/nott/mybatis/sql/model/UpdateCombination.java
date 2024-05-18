package org.nott.mybatis.sql.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Nott
 * @date 2024-5-14
 */

@Setter
@Getter
@AllArgsConstructor
public class UpdateCombination {

    private String key;

    private Object value;
}
