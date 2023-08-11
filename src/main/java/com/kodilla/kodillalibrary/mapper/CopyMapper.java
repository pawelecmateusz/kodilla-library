package com.kodilla.kodillalibrary.mapper;

import com.kodilla.kodillalibrary.domain.Copy;
import com.kodilla.kodillalibrary.domain.dto.CopyDto;
import com.kodilla.kodillalibrary.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CopyMapper {

    private final BookService bookService;

    public Copy mapToCopy(final CopyDto copyDto) {
        return Copy.builder()
                .copyId(copyDto.getCopyId())
                .book(bookService.getBookById(copyDto.getBookId()))
                .status(copyDto.getStatus())
                .build();
    }

    public CopyDto mapToCopyDto(final Copy copy) {
        return new CopyDto(
                copy.getCopyId(),
                copy.getBook().getBookId(),
                copy.getStatus()
        );
    }

    public List<CopyDto> mapToCopyDtoList(final List<Copy> copies) {
        return copies.stream()
                .map(this::mapToCopyDto)
                .toList();
    }

}