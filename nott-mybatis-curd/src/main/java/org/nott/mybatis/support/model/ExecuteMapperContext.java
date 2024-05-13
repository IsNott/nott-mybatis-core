package org.nott.mybatis.support.model;

import lombok.Data;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 线程执行的mapper上下文内容
 * @author Nott
 * @date 2024-5-13
 */

@Data
public class ExecuteMapperContext {

    /**
     * 当前mapper的class
     */
    private Class<?> currentMapperClass;

    /**
     * 当前mapper的泛型
     */
    private Class<?> currentGenericSuperClass;

    /**
     * 执行的方法
     */
    private Method executeMethod;

    /**
     * 参数
     */
    private Parameter[] parameters;
}
