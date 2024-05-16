package org.nott.mybatis.exception;

/**
 * @author Nott
 * @date 2024-5-16
 */
public class SqlBuilderException extends RuntimeException {

    public SqlBuilderException() {
    }

    public SqlBuilderException(String message) {
        super(message);
    }
}
