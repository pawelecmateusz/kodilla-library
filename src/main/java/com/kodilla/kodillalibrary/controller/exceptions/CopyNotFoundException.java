package com.kodilla.kodillalibrary.controller.exceptions;

public class CopyNotFoundException extends Exception {
    public CopyNotFoundException() {
        super("There is no such copy in the library");
    }
}
