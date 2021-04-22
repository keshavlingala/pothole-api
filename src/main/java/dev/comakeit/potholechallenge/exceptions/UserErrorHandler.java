package dev.comakeit.potholechallenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserErrorHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public UserAlreadyExistsException handleCustomException(UserAlreadyExistsException ce) {
        return ce;
    }

    @ExceptionHandler(InvalidUserNamePasswordException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public InvalidUserNamePasswordException handleInvalidUser(InvalidUserNamePasswordException err) {
        return err;
    }
}
