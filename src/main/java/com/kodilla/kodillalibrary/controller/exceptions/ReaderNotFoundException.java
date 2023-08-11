package com.kodilla.kodillalibrary.controller.exceptions;

public class ReaderNotFoundException extends Exception {
    public ReaderNotFoundException() {
        super("There is no such reader");
    }
}
