package com.platzi_play.domain.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(Long id) {
        super("La película con id " + id + " no existe");
    }
}
