package com.kodilla.kodillalibrary.controller.exceptions;

public class ReaderHasRentsException extends Exception {
    public ReaderHasRentsException() {
        super("Reader still has rents");
    }
}
