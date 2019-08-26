package com.duallab.busschedule.writer.exception;

public class WriteResultException extends Exception {
    public WriteResultException(String message) {
        super(message);
    }

    public WriteResultException(String message, Throwable cause) {
        super(message, cause);
    }
}
