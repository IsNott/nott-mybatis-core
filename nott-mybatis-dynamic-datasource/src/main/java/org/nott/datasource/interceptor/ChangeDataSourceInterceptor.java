package org.nott.datasource.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.nott.datasource.DynamicDataSourceHolder;
import org.nott.datasource.annotations.DataSource;
import org.nott.datasource.constant.DataSourceConstant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * @author Nott
 * @date 2024-5-21
 */

@Aspect
@Component
public class ChangeDataSourceInterceptor {

    @Around("@annotation(org.nott.datasource.annotations.DataSource) || @annotation(org.springframework.transaction.annotation.Transactional)")
    public Object around(ProceedingJoinPoint point) {

        MethodSignature signature = (MethodSignature) point.getSignature();

        Method method = signature.getMethod();

        String value = DataSourceConstant.DEFAULT_DB;

        boolean isMainDb = method.isAnnotationPresent(Transactional.class);

        boolean isCustomDataSource = method.isAnnotationPresent(DataSource.class);

        if (isCustomDataSource && !isMainDb) {
            DataSource annotation = method.getAnnotation(DataSource.class);
            value = annotation.value();
        }
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
