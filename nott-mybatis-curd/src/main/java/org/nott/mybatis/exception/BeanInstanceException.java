package org.nott.mybatis.exception;

/**
 * 对象实例化异常
 * @author Nott
 * @date 2024-5-14
 */
public class BeanInstanceException extends RuntimeException {

    public BeanInstanceException(String message) {
        super(message);
    }
}
