package com.kodilla.kodillalibrary.controller.exceptions;

public class CopyCurrentlyRented extends Exception {
    public CopyCurrentlyRented() {
        super("Copy is currently rented");
    }
}
