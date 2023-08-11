package com.kodilla.kodillalibrary.entities;

import com.kodilla.kodillalibrary.controller.exceptions.BookAlreadyReturned;
import com.kodilla.kodillalibrary.controller.exceptions.RentNotFoundException;
import com.kodilla.kodillalibrary.domain.Book;
import com.kodilla.kodillalibrary.domain.Copy;
import com.kodilla.kodillalibrary.domain.Reader;
import com.kodilla.kodillalibrary.domain.Rent;
import com.kodilla.kodillalibrary.repository.RentRepository;
import com.kodilla.kodillalibrary.service.BookService;
import com.kodilla.kodillalibrary.service.CopyService;
import com.kodilla.kodillalibrary.service.ReaderService;
import com.kodilla.kodillalibrary.service.RentService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
public class RentEntityTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private CopyService copyService;

    @Autowired
    private ReaderService readerService;

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

        bookService.saveBook(book);
        copyService.saveCopy(copy);
        readerService.saveReader(reader);

        //When
        rentService.rentABook(copy, reader);
        List<Rent> rents = rentRepository.findAll();
        Long rentId = rents.get(0).getRentId();
        System.out.println(rentId);

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

        bookService.saveBook(book);
        readerService.saveReader(reader);
        rentService.saveRent(rent);
        List<Rent> rents = rentRepository.findAll();
        Long rentId = rents.get(0).getRentId();

        //When
        try {
            rentService.returnABook(rentId);
        }
        catch (BookAlreadyReturned | RentNotFoundException e) {
            System.out.println(e.getMessage());
        }

        LocalDate rentReturnDate = rentRepository.findById(rentId).get().getReturnDate();

        //Then
        assertEquals("available", rentRepository.findById(rent.getRentId()).get().getCopy().getStatus());
        assertEquals(rentReturnDate, LocalDate.now());

    }
}
