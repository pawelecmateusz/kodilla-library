package com.kodilla.kodillalibrary.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Long bookId;

    private String title;

    private String author;

    private int pubYear;

}