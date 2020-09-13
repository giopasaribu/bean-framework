package com.quiz.beanframework.exception;

public class BeanCreationException extends RuntimeException {

    private static final long serialVersionUID = -1814095963694107206L;

    public BeanCreationException() {
        super();
    }

    public BeanCreationException(String message) {
        super(message);
    }

    public BeanCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanCreationException(Throwable cause) {
        super(cause);
    }

    protected BeanCreationException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
