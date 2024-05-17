package org.nott.mybatis.exception;

/**
 * 生成SQL异常
 * @author Nott
 * @date 2024-5-14
 */
public class SqlParseException extends RuntimeException {

    public SqlParseException(String message) {
        super(message);
    }

    public SqlParseException(Throwable cause) {
        super(cause);
    }
}
