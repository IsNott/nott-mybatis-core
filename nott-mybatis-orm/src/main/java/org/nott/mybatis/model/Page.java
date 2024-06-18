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

    private Long totalPage = 0L;

    private Integer currentPage = 1;

    private Integer size = 10;

    public Page() {
    }

    public Page(Integer currentPage, Integer size) {
        if (currentPage > 0) {
            this.currentPage = currentPage;
        }
        if (size > 0) {
            this.size = size;
        }
    }

    public void setTotalResult(Long totalResult) {
        this.totalResult = totalResult;
        this.totalPage = (this.totalResult % this.size == 0) ? this.totalResult / this.size : this.totalResult / this.size + 1;
    }
}
