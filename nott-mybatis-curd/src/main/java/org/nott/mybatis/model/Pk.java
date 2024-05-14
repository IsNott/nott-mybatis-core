package org.nott.mybatis.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Nott
 * @date 2024-5-14
 */
@Setter
@Getter
@Builder
public class Pk {

//    public Pk() {
//    }

    private Class<?> type;

    private String name;
}
