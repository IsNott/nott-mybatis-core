package org.nott.mybatis.support.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.nott.mybatis.support.model.ExecuteMapperContextBean;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/**
 * @author Nott
 * @date 2024-5-13
 */

@Component
@Slf4j
public class MybatisAopInterceptor implements MethodInterceptor {

    private static final ThreadLocal<ExecuteMapperContextBean> mapperContextThreadLocal = new ThreadLocal<>();

    public void set(MethodInvocation invocation){
        ExecuteMapperContextBean bean = new ExecuteMapperContextBean();

        Class<?> aClass = invocation.getThis().getClass();

        Class<?>[] interfacesForClass = ClassUtils.getAllInterfacesForClass(aClass, aClass.getClassLoader());

        bean.setCurrentMapperClass(interfacesForClass[0]);

        bean.setParameters(invocation.getMethod().getParameters());

        bean.setExecuteMethod(invocation.getMethod());

        mapperContextThreadLocal.set(bean);
    }


    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        this.set(invocation);

        Object result = null;
        try {
            result = invocation.proceed();
            return result;
        } catch (Exception e) {
            throw  e;
        } finally {
            //释放线程中数据
            mapperContextThreadLocal.remove();
        }
    }

    public static ThreadLocal<ExecuteMapperContextBean> getContext(){
        return mapperContextThreadLocal;
    }
}
