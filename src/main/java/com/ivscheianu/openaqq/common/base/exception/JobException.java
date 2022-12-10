package com.ivscheianu.openaqq.common.base.exception;

public class JobException extends RuntimeException {

    public JobException(final String message) {
        super(message);
    }

    public JobException(final Throwable cause) {
        super(cause);
    }

    public JobException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
