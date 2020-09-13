package com.quiz.beanframework.exception;

public class DefaultConstructorNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3705093238936508293L;

    public DefaultConstructorNotFoundException() {
        super();
    }

    public DefaultConstructorNotFoundException(String message) {
        super(message);
    }

    public DefaultConstructorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DefaultConstructorNotFoundException(Throwable cause) {
        super(cause);
    }

    protected DefaultConstructorNotFoundException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
