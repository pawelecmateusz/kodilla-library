package com.kodilla.kodillalibrary.entities;

import com.kodilla.kodillalibrary.domain.Book;
import com.kodilla.kodillalibrary.domain.Copy;
import com.kodilla.kodillalibrary.domain.Reader;
import com.kodilla.kodillalibrary.domain.Rent;
import com.kodilla.kodillalibrary.repository.BookRepository;
import com.kodilla.kodillalibrary.repository.CopyRepository;
import com.kodilla.kodillalibrary.service.BookService;
import com.kodilla.kodillalibrary.service.ReaderService;
import com.kodilla.kodillalibrary.service.RentService;
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
    private ReaderService readerService;

    @Autowired
    private RentService rentService;


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

        bookService.saveBook(book);
        readerService.saveReader(reader);
        rentService.saveRent(rent);

        //When
        bookService.deleteBookById(book.getBookId());

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

        bookService.saveBook(book);

        //When
        bookService.deleteBookById(book.getBookId());

        //Then
        assertTrue(bookRepository.findById(book.getBookId()).isPresent());
        assertFalse(copyRepository.findById(copy.getCopyId()).isPresent());
        assertTrue(copyRepository.findById(copy2.getCopyId()).isPresent());
    }

}