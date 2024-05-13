package org.nott.mybatis.support.aop;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Nott
 * @date 2024-5-13
 */

public class ConCurrentMapperAopFactory {

    public ConCurrentMapperAopFactory() {
    }

    public static final ConCurrentMapperAopFactory FACTORY = new ConCurrentMapperAopFactory();

    /**
     * mapper与所属泛型的map
     */
    public static final Map<String,Class<?>> mapperGenericClassMap = new ConcurrentHashMap<>();

    /**
     *mapper与所属实体的map
     */
    public static final Map<String,Object> mapperBeanMap = new ConcurrentHashMap<>();

    // todo 获取泛型并实例化bean
    public static Object getCurrentMapperBean(){
        return null;
    }

    // todo 获取泛型
    public static Class<?> getCurrentMapperGenericClass(){
        return null;
    }




}
