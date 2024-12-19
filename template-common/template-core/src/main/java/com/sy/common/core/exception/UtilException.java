package com.sy.common.core.exception;

/**
 * @author Monster
 * @version v1.0
 */
public class UtilException extends RuntimeException {

    public UtilException(String message) {
        super(message);
    }

    public UtilException(Throwable e) {
        super(e.getMessage(), e);
    }

    public UtilException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
