package com.kodilla.kodillalibrary.controller.exceptions;

public class CopyCurrentlyRentedException extends Exception {
    public CopyCurrentlyRentedException() {
        super("Copy is currently rented");
    }
}
