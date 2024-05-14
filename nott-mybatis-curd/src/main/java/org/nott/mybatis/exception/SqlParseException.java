package org.nott.mybatis.exception;

/**
 * @author Nott
 * @date 2024-5-14
 */
public class SqlParseException extends RuntimeException {

    public SqlParseException(String message) {
        super(message);
    }
}
