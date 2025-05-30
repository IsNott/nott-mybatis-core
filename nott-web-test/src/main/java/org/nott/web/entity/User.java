package org.nott.web.entity;

import lombok.Data;
import org.nott.mybatis.annotations.TableId;

import java.io.Serializable;

/**
 * @author Nott
 * @date 2024-5-10
 */
@Data
public class User implements Serializable {

    @TableId("id")
    private String id;

    private String name;

    private String email;

    private Integer age;

    private String password;
}
