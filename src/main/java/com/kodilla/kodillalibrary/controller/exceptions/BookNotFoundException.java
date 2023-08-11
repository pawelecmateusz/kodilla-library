package com.kodilla.kodillalibrary.controller.exceptions;

public class BookNotFoundException extends Exception {
    public BookNotFoundException() {
        super("There is no such book");
    }
}
