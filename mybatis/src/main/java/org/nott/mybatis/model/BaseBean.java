package org.nott.mybatis.model;

import com.alibaba.fastjson2.JSON;

import java.io.Serializable;

/**
 * @author Nott
 * @date 2024-5-10
 */
public abstract class BaseBean implements Serializable {

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
