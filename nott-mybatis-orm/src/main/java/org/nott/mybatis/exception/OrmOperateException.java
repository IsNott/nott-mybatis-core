package org.nott.mybatis.exception;

public class OrmOperateException extends RuntimeException{

    public OrmOperateException() {
    }

    public OrmOperateException(String message) {
        super(message);
    }

    public OrmOperateException(Throwable cause) {
        super(cause);
    }
}
