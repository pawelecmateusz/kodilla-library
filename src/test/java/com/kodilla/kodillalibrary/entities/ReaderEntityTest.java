package com.kodilla.kodillalibrary.entities;

import com.kodilla.kodillalibrary.controller.exceptions.ReaderHasRentsException;
import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.repository.*;
import com.kodilla.kodillalibrary.service.ReaderService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class ReaderEntityTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private RentRepository rentRepository;

    @Test
    void shouldNotDeleteReader() {

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
        Reader reader = Reader.builder()
                .build();
        Rent rent = Rent.builder()
                .copy(copy)
                .reader(reader)
                .build();

        book.getCopies().add(copy);
        reader.getRents().add(rent);
        bookRepository.save(book);
        copyRepository.save(copy);
        readerRepository.save(reader);
        rentRepository.save(rent);

        //When
        assertThrows(ReaderHasRentsException.class, () ->
                readerService.deleteReaderById(reader.getReaderId()));

        //Then
        assertTrue(readerRepository.findById(reader.getReaderId()).isPresent());
        assertTrue(rentRepository.findById(rent.getRentId()).isPresent());
    }

    @Test
    void shouldDeleteRentWhenDeletingReader() {

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
        Reader reader = Reader.builder()
                .build();
        Rent rent = Rent.builder()
                .copy(copy)
                .reader(reader)
                .build();

        book.getCopies().add(copy);
        reader.getRents().add(rent);
        rent.setRentDate(LocalDate.now());
        rent.setReturnDate(LocalDate.now().plusDays(7));
        bookRepository.save(book);
        copyRepository.save(copy);
        readerRepository.save(reader);
        rentRepository.save(rent);

        //When
        assertDoesNotThrow(() ->
                readerService.deleteReaderById(reader.getReaderId()));

        //Then
        assertFalse(readerRepository.findById(reader.getReaderId()).isPresent());
        assertFalse(rentRepository.findById(rent.getRentId()).isPresent());
    }

}