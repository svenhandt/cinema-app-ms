package com.svenhandt.app.cinemaapp.ordersms.domain.command.interceptor.exception;

public class BookingAlreadyInvalidException extends RuntimeException {

    public BookingAlreadyInvalidException() {
    }

    public BookingAlreadyInvalidException(String message) {
        super(message);
    }

    public BookingAlreadyInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookingAlreadyInvalidException(Throwable cause) {
        super(cause);
    }

    public BookingAlreadyInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
