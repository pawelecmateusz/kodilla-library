package com.kodilla.kodillalibrary.controller.exceptions;

public class BookAlreadyReturnedException extends Exception {
    public BookAlreadyReturnedException() {
        super("This book is already returned");
    }
}
