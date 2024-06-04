package org.nott.mybatis.support.aop.Interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.nott.mybatis.support.aop.model.ExecuteMapperContextBean;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;


/**
 * @author Nott
 * @date 2024-5-13
 */

@Component
public class MybatisAopInterceptor implements MethodInterceptor {

    private static final ThreadLocal<ExecuteMapperContextBean> mapperContextThreadLocal = new ThreadLocal<>();

    public void set(MethodInvocation invocation){
        ExecuteMapperContextBean bean = new ExecuteMapperContextBean();

        Class<?> aClass = Objects.requireNonNull(invocation.getThis()).getClass();

        Class<?>[] interfacesForClass = ClassUtils.getAllInterfacesForClass(aClass, aClass.getClassLoader());

        Class<?>[] interfaces = interfacesForClass[0].getInterfaces();

        bean.setExtendMapperClass(interfaces[0]);

        bean.setCurrentMapperClass(interfacesForClass[0]);

        bean.setParameters(invocation.getMethod().getParameters());

        bean.setExecuteMethod(invocation.getMethod());

        mapperContextThreadLocal.set(bean);
    }

    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        this.set(invocation);

        Object result;
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
