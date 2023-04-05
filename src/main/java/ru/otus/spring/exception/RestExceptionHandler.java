package ru.otus.spring.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        ApiError apiError = new ApiError("Malformed JSON Request", ex.getMessage(), requestUri);
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiError> handleBookNotFoundException(BookNotFoundException ex) {
        ApiError apiError = new ApiError("Book not found", ex.getMessage(), "");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
