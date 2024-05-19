package org.nott.mybatis.support.aop.model;

import lombok.Data;
import org.nott.mybatis.model.Page;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 线程执行的mapper上下文内容
 * @author Nott
 * @date 2024-5-13
 */

@Data
public class ExecuteMapperContextBean {

    /**
     * 当前mapper的class
     */
    private Class<?> currentMapperClass;

    /**
     * 执行的方法
     */
    private Method executeMethod;

    /**
     * 参数
     */
    private Parameter[] parameters;

    /**
     * 继承的mapper
     */
    private Class<?> extendMapperClass;


}
