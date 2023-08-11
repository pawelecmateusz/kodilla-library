package com.kodilla.kodillalibrary.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RentDto {

    private Long rentId;

    private Long copyId;

    private Long readerId;

    private LocalDate rentDate;

    private LocalDate returnDate;

}