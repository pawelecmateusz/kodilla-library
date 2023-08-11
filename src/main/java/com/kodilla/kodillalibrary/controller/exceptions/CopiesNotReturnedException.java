package com.kodilla.kodillalibrary.controller.exceptions;

public class CopiesNotReturnedException extends Exception {
    public CopiesNotReturnedException() {
        super("Not all copies are returned");
    }
}
