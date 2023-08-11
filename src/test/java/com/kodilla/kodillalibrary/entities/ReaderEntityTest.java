package com.kodilla.kodillalibrary.entities;

import com.kodilla.kodillalibrary.domain.Book;
import com.kodilla.kodillalibrary.domain.Copy;
import com.kodilla.kodillalibrary.domain.Reader;
import com.kodilla.kodillalibrary.domain.Rent;
import com.kodilla.kodillalibrary.repository.ReaderRepository;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Transactional
@SpringBootTest
public class ReaderEntityTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private CopyService copyService;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private RentService rentService;

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
        bookService.saveBook(book);
        copyService.saveCopy(copy);
        readerService.saveReader(reader);
        rentService.saveRent(rent);

        //When
        readerService.deleteReaderById(reader.getReaderId());

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
        bookService.saveBook(book);
        copyService.saveCopy(copy);
        readerService.saveReader(reader);
        rentService.saveRent(rent);

        //When
        readerService.deleteReaderById(reader.getReaderId());

        //Then
        assertFalse(readerRepository.findById(reader.getReaderId()).isPresent());
        assertFalse(rentRepository.findById(rent.getRentId()).isPresent());
    }

}