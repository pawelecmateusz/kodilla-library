package com.kodilla.kodillalibrary.controller;

import com.kodilla.kodillalibrary.controller.exceptions.BookNotFoundException;
import com.kodilla.kodillalibrary.controller.exceptions.CopiesNotReturnedException;
import com.kodilla.kodillalibrary.domain.Book;
import com.kodilla.kodillalibrary.controller.dto.BookDto;
import com.kodilla.kodillalibrary.mapper.BookMapper;
import com.kodilla.kodillalibrary.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/books")
@CrossOrigin("*")
@RequiredArgsConstructor
public class BookController {

    private final BookMapper bookMapper;

    @Autowired
    private final BookService bookService;

    @GetMapping(value = "{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long bookId) throws BookNotFoundException {
        return ResponseEntity.ok(bookMapper.mapToBookDto(bookService.getBookById(bookId)));
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookMapper.mapToBookDtoList(bookService.getAllBooks()));
    }

    @GetMapping(value = "available/{bookId}")
    public ResponseEntity<Integer> getAvailableCopiesCount(@PathVariable Long bookId) {
        int availableCopiesCount = bookService.getAvailableCopies(bookId);
        return ResponseEntity.ok(availableCopiesCount);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        Book book = bookMapper.mapToBook(bookDto);
        bookService.saveBook(book);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) throws CopiesNotReturnedException, BookNotFoundException {
        bookService.deleteBookById(bookId);
        return ResponseEntity.ok().build();
    }

}