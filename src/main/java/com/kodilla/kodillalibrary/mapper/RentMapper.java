package com.kodilla.kodillalibrary.mapper;

import com.kodilla.kodillalibrary.controller.exceptions.CopyNotFoundException;
import com.kodilla.kodillalibrary.controller.exceptions.ReaderNotFoundException;
import com.kodilla.kodillalibrary.domain.Rent;
import com.kodilla.kodillalibrary.domain.dto.RentDto;
import com.kodilla.kodillalibrary.service.CopyService;
import com.kodilla.kodillalibrary.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentMapper {
    private final ReaderService readerService;

    private final CopyService copyService;

    public Rent mapToRent(final RentDto rentDto) throws CopyNotFoundException, ReaderNotFoundException {
        return Rent.builder()
                .rentId(rentDto.getRentId())
                .copy(copyService.getCopyById(rentDto.getCopyId()))
                .reader(readerService.getReaderById(rentDto.getReaderId()))
                .build();
    }

    public RentDto mapToRentDto(final Rent rent) {
        return new RentDto(
                rent.getRentId(),
                rent.getCopy().getCopyId(),
                rent.getReader().getReaderId(),
                rent.getRentDate(),
                rent.getReturnDate()
        );
    }

    public List<RentDto> mapToRentDtoList(final List<Rent> rents) {
        return rents.stream()
                .map(this::mapToRentDto)
                .toList();
    }

}