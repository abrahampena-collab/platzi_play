package com.platzi_play.web.exception;

import com.platzi_play.domain.exception.MovieAlreadyExistException;
import com.platzi_play.domain.exception.MovieNotFoundException;
import com.platzi_play.web.exception.ErrorData;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    // Cambiamos 'Exception ex' por la excepción específica 'MovieAlreadyExistException ex'
    @ExceptionHandler(MovieAlreadyExistException.class)
    public ResponseEntity<ErrorData> handleMovieAlreadyExist(MovieAlreadyExistException ex){
        ErrorData error = new ErrorData("movie-already-exist", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    // Ahora usa nuestra clase personalizada ErrorData con estado 404
    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorData> handleMovieNotFound(MovieNotFoundException ex) {
        ErrorData error = new ErrorData("movie-not-found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorData>> handleException(MethodArgumentNotValidException ex) {
        List<ErrorData> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(new ErrorData(error.getField(), error.getDefaultMessage()));
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorData> handleException(Exception ex){
        ErrorData error = new ErrorData("exception", ex.getMessage());

        return ResponseEntity.internalServerError().body(error);
    }

}
