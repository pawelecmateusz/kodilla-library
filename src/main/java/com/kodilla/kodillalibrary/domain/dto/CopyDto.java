package com.kodilla.kodillalibrary.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CopyDto {

    private Long copyId;

    private Long bookId;

    private String status;

}