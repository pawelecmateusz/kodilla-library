package com.kodilla.kodillalibrary.controller.exceptions;

public class BookAlreadyReturned extends Exception {
    public BookAlreadyReturned() {
        super("This book is already returned");
    }
}
