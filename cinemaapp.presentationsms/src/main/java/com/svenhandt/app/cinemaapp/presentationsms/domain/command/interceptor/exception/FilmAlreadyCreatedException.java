package com.svenhandt.app.cinemaapp.presentationsms.domain.command.interceptor.exception;

public class FilmAlreadyCreatedException extends RuntimeException {

    public FilmAlreadyCreatedException() {
    }

    public FilmAlreadyCreatedException(String message) {
        super(message);
    }

    public FilmAlreadyCreatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmAlreadyCreatedException(Throwable cause) {
        super(cause);
    }

    public FilmAlreadyCreatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
