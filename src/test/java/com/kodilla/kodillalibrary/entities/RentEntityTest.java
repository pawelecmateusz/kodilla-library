package com.kodilla.kodillalibrary.entities;

import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.repository.*;
import com.kodilla.kodillalibrary.service.RentService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class RentEntityTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private RentService rentService;

    @Test
    void shouldRentABook() {

        //Given
        Book book = Book.builder()
                .title("Title")
                .build();
        Copy copy = Copy.builder()
                .book(book)
                .status("available")
                .build();
        Reader reader = Reader.builder()
                .build();

        bookRepository.save(book);
        copyRepository.save(copy);
        readerRepository.save(reader);

        //When
        assertDoesNotThrow(() ->
                rentService.rentABook(copy, reader));

        List<Rent> rents = (List<Rent>) rentRepository.findAll();
        Long rentId = rents.get(0).getRentId();

        //Then
        assertTrue(rentRepository.existsById(rentId));
    }

    @Test
    void shouldReturnABook() {

        //Given
        Book book = Book.builder()
                .title("Title")
                .build();
        Copy copy = Copy.builder()
                .book(book)
                .status("rented")
                .build();
        Reader reader = Reader.builder()
                .build();
        Rent rent = Rent.builder()
                .copy(copy)
                .reader(reader)
                .rentDate(LocalDate.now())
                .build();

        book.getCopies().add(copy);
        reader.getRents().add(rent);
        copy.setRent(rent);

        bookRepository.save(book);
        readerRepository.save(reader);
        rentRepository.save(rent);
        List<Rent> rents = (List<Rent>) rentRepository.findAll();
        Long rentId = rents.get(0).getRentId();

        //When
        assertDoesNotThrow(() ->
                rentService.returnABook(rentId));

        LocalDate rentReturnDate = rentRepository.findById(rentId).get().getReturnDate();

        //Then
        assertEquals("available", rentRepository.findById(rent.getRentId()).get().getCopy().getStatus());
        assertEquals(rentReturnDate, LocalDate.now());
    }

}