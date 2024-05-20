package org.nott.datasource.exception;

/**
 * @author Nott
 * @date 2024-5-20
 */
public class DynamicInitException extends RuntimeException{

    public DynamicInitException() {
    }

    public DynamicInitException(String message) {
        super(message);
    }

    public DynamicInitException(String message, Throwable cause) {
        super(message, cause);
    }
}
