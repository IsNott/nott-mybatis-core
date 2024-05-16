package org.nott.mybatis.model;

import lombok.Data;

import java.util.List;

/**
 * @author Nott
 * @date 2024-5-15
 */

@Data
public class Page<T> {

    private List<T> records;

    private Long totalResult;

    private Integer totalPage = 0;

    private Integer currentPage = 1;

    private Integer size = 10;

}
