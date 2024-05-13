package org.nott.mybatis.support.aop;

import org.nott.mybatis.support.model.ExecuteMapperContext;

/**
 * 拦截mapper 环绕
 * @author Nott
 * @date 2024-5-13
 */

public class MapperThreadContextAop {


    private static final ThreadLocal<ExecuteMapperContext> mapperContextThreadLocal = new ThreadLocal<>();
}
