package com.kodilla.kodillalibrary.entities;

import com.kodilla.kodillalibrary.controller.exceptions.CopyCurrentlyRentedException;
import com.kodilla.kodillalibrary.controller.exceptions.CopyNotFoundException;
import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.repository.*;
import com.kodilla.kodillalibrary.service.CopyService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class CopyEntityTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private CopyService copyService;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private RentRepository rentRepository;


    @Test
    void shouldNotDeleteCopy() {

        //Given
        Book book = Book.builder()
                .title("Title")
                .author("Author")
                .pubYear(2000)
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
                .build();

        copy.setRent(rent);
        book.getCopies().add(copy);
        readerRepository.save(reader);
        bookRepository.save(book);
        rentRepository.save(rent);
        copyRepository.save(copy);

        //When
        assertThrows(CopyCurrentlyRentedException.class, () ->
                copyService.deleteCopyById(copy.getCopyId()));

        //Then
        assertTrue(copyRepository.findById(copy.getCopyId()).isPresent());
        assertTrue(rentRepository.findById(rent.getRentId()).isPresent());

    }

    @Test
    void shouldDeleteRentWhenDeletingCopy() throws CopyNotFoundException, CopyCurrentlyRentedException {

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

        copy.setRent(rent);
        book.getCopies().add(copy);
        readerRepository.save(reader);
        bookRepository.save(book);
        rentRepository.save(rent);
        copyRepository.save(copy);

        //When
        assertDoesNotThrow(() ->
                copyService.deleteCopyById(copy.getCopyId()));

        //Then
        assertFalse(copyRepository.findById(copy.getCopyId()).isPresent());
        assertFalse(rentRepository.findById(rent.getRentId()).isPresent());

    }

    @Test
    void shouldUpdateRentWhenUpdatingCopy() {

        //Given
        Book book = Book.builder()
                .title("Title1")
                .author("Author1")
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
                .rentDate(LocalDate.now())
                .returnDate(LocalDate.now().plusDays(1))
                .build();

        copy.setRent(rent);
        book.getCopies().add(copy);
        readerRepository.save(reader);
        bookRepository.save(book);
        rentRepository.save(rent);
        copyRepository.save(copy);

        //When
        Copy savedCopy = copyRepository.save(copy);
        savedCopy.setStatus("rented");

        List<Copy> updatedCopy = (List<Copy>) copyRepository.findAll();
        List<Rent> updatedRent = (List<Rent>) rentRepository.findAll();

        //Then
        assertEquals("rented", updatedCopy.get(0).getStatus());
        assertEquals("rented", updatedRent.get(0).getCopy().getStatus());
    }

}