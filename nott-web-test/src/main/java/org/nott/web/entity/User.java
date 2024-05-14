package org.nott.web.entity;

import lombok.Data;
import org.nott.mybatis.annotations.TableId;

/**
 * @author Nott
 * @date 2024-5-10
 */
@Data
public class User {

    @TableId("id")
    private String id;

    private String name;

    private String email;

    private Integer age;

    private String password;
}
