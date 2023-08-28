package com.kodilla.kodillalibrary.controller.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookAlreadyReturnedException.class)
    public ResponseEntity<ErrorInfo> handleBookAlreadyReturnedException(HttpServletRequest request, BookAlreadyReturnedException exception) {
        ErrorInfo errorInfo = new ErrorInfo(request, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorInfo);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleBookNotFoundException(HttpServletRequest request, BookNotFoundException exception) {
        ErrorInfo errorInfo = new ErrorInfo(request, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorInfo);
    }

    @ExceptionHandler(CopiesNotReturnedException.class)
    public ResponseEntity<ErrorInfo> handleCopiesNotReturnedException(HttpServletRequest request, CopiesNotReturnedException exception) {
        ErrorInfo errorInfo = new ErrorInfo(request, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorInfo);
    }

    @ExceptionHandler(CopyCurrentlyRentedException.class)
    public ResponseEntity<ErrorInfo> handleCopyCurrentlyRentedException(HttpServletRequest request, CopyCurrentlyRentedException exception) {
        ErrorInfo errorInfo = new ErrorInfo(request, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorInfo);
    }

    @ExceptionHandler(CopyNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleCopyNotFoundException(HttpServletRequest request, CopyNotFoundException exception) {
        ErrorInfo errorInfo = new ErrorInfo(request, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorInfo);
    }

    @ExceptionHandler(ReaderHasRentsException.class)
    public ResponseEntity<ErrorInfo> handleReaderHasRentsException(HttpServletRequest request, ReaderHasRentsException exception) {
        ErrorInfo errorInfo = new ErrorInfo(request, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorInfo);
    }

    @ExceptionHandler(ReaderNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleReaderNotFoundException(HttpServletRequest request, ReaderNotFoundException exception) {
        ErrorInfo errorInfo = new ErrorInfo(request, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorInfo);
    }

    @ExceptionHandler(RentNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleRentNotFoundException(HttpServletRequest request, RentNotFoundException exception) {
        ErrorInfo errorInfo = new ErrorInfo(request, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorInfo);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ErrorInfo handleMethodNotSupported(HttpServletRequest request, Exception exception) {
        return new ErrorInfo(request, exception);
    }
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo handleMultipartException(HttpServletRequest request) {
        return new ErrorInfo(request, "Wrong Json format");
    }

}