package com.kodilla.kodillalibrary.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReaderDto {

    private Long readerId;

    private String firstname;

    private String lastname;

    private LocalDate joinDate;

}