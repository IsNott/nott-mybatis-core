package org.nott.datasource.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.nott.datasource.DynamicDataSourceHolder;
import org.nott.datasource.annotations.DataSource;
import java.lang.reflect.Method;

/**
 * @author Nott
 * @date 2024-5-21
 */

@Aspect
public class ChangeDataSourceInterceptor {

    @Around("@annotation(org.nott.datasource.annotations.DataSource)")
    public Object around(ProceedingJoinPoint point){

        MethodSignature signature = (MethodSignature) point.getSignature();

        Method method = signature.getMethod();

        DataSource annotation = method.getAnnotation(DataSource.class);

        String value = annotation.value();

        DynamicDataSourceHolder.setDynamicDataSourceKey(value);

        Object result;
        try {
            result = point.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
