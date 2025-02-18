package ru.skypro.homework.error;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * Перехватчик и обработчик исключений. Обрабатываемые типы исключений: {@link EntityModelNotFoundException},
 * {@link InvalidRequestException}, {@link IOException}
 */
@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler(EntityModelNotFoundException.class)
    public ResponseEntity<String> entityModelNotFoundExceptionHandler(EntityModelNotFoundException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> invalidRequestExceptionHandler(InvalidRequestException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> IOExceptionHandler(IOException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}