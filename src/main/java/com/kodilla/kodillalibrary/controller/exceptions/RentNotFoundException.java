package com.kodilla.kodillalibrary.controller.exceptions;

public class RentNotFoundException extends Exception {
    public RentNotFoundException() {
        super("There is no such rent");
    }
}
