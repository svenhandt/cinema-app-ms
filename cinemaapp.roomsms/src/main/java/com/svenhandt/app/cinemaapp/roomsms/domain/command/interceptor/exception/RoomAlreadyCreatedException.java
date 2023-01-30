package com.svenhandt.app.cinemaapp.roomsms.domain.command.interceptor.exception;

public class RoomAlreadyCreatedException extends RuntimeException {

    public RoomAlreadyCreatedException() {
    }

    public RoomAlreadyCreatedException(String message) {
        super(message);
    }

    public RoomAlreadyCreatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoomAlreadyCreatedException(Throwable cause) {
        super(cause);
    }

    public RoomAlreadyCreatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
