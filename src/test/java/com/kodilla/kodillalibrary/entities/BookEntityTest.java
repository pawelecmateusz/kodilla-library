package com.kodilla.kodillalibrary.entities;

import com.kodilla.kodillalibrary.controller.exceptions.CopiesNotReturnedException;
import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.repository.*;
import com.kodilla.kodillalibrary.service.BookService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class BookEntityTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private RentRepository rentRepository;

    @Test
    void shouldDeleteBookAndAllCopies() {

        //Given
        Book book = Book.builder()
                .title("Title")
                .author("Author")
                .pubYear(2000)
                .build();
        Copy copy = Copy.builder()
                .book(book)
                .status("available")
                .build();
        Copy copy2 = Copy.builder()
                .book(book)
                .status("available")
                .build();
        Copy copy3 = Copy.builder()
                .book(book)
                .status("available")
                .build();

        Reader reader = Reader.builder()
                .firstname("Firstname")
                .lastname("Lastname")
                .build();

        Rent rent = Rent.builder()
                .copy(copy)
                .reader(reader)
                .build();

        book.getCopies().add(copy);
        book.getCopies().add(copy2);
        book.getCopies().add(copy3);

        bookRepository.save(book);
        readerRepository.save(reader);
        rentRepository.save(rent);

        //When
        assertDoesNotThrow(() -> {
            bookService.deleteBookById(book.getBookId());
        });

        //Then
        assertFalse(bookRepository.findById(book.getBookId()).isPresent());
        assertFalse(copyRepository.findById(copy.getCopyId()).isPresent());
        assertFalse(copyRepository.findById(copy2.getCopyId()).isPresent());
        assertFalse(copyRepository.findById(copy3.getCopyId()).isPresent());

    }
    @Test
    void shouldDeleteBookAndAvailableCopies() {

        //Given
        Book book = Book.builder()
                .title("Title")
                .author("Author")
                .pubYear(2000)
                .build();
        Copy copy = Copy.builder()
                .book(book)
                .status("available")
                .build();

        Copy copy2 = Copy.builder()
                .book(book)
                .status("rent")
                .build();

        book.getCopies().add(copy);
        book.getCopies().add(copy2);

        bookRepository.save(book);

        //When

        assertThrows(CopiesNotReturnedException.class, () ->
                bookService.deleteBookById(book.getBookId()));

        //Then
        assertTrue(bookRepository.findById(book.getBookId()).isPresent());
        assertFalse(copyRepository.findById(copy.getCopyId()).isPresent());
        assertTrue(copyRepository.findById(copy2.getCopyId()).isPresent());
    }

}