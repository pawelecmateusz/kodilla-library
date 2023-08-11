package com.kodilla.kodillalibrary.mapper;

import com.kodilla.kodillalibrary.domain.Book;
import com.kodilla.kodillalibrary.domain.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookMapper {

    public Book mapToBook(final BookDto bookDto) {
        return Book.builder()
                .bookId(bookDto.getBookId())
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .pubYear(bookDto.getPubYear())
                .build();
    }

    public BookDto mapToBookDto(final Book book) {
        return new BookDto(
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPubYear());
    }

    public List<BookDto> mapToBookDtoList(final List<Book> books) {
        return books.stream()
                .map(this::mapToBookDto)
                .toList();
    }

}