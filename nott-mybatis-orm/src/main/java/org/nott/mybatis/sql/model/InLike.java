package org.nott.mybatis.sql.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.nott.mybatis.sql.enums.LikeMode;

/**
 * @author Nott
 * @date 2024-5-16
 */

@Setter
@Getter
@Builder
public class InLike {

    private String colum;

    private String value;

    private LikeMode likeMode;

    public static InLike choose(String colum, String value, LikeMode likeMode) {
        return InLike.builder().colum(colum).value(value).likeMode(likeMode).build();
    }
}
