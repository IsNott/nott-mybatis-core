package org.nott.web.entity;

import lombok.Data;

/**
 * @author Nott
 * @date 2024-5-10
 */
@Data
public class User {

    private String id;

    private String name;

    private String email;

    private String password;
}
