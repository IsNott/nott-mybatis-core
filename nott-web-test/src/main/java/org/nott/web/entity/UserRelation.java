package org.nott.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Nott
 * @date 2024-6-5
 */

@Data
public class UserRelation implements Serializable {

    private String id;

    private String userId;

    private String relationContent;
}
