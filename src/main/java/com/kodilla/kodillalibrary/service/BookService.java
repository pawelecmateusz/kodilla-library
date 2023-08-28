package com.kodilla.kodillalibrary.service;

import com.kodilla.kodillalibrary.controller.exceptions.BookNotFoundException;
import com.kodilla.kodillalibrary.controller.exceptions.CopiesNotReturnedException;
import com.kodilla.kodillalibrary.domain.Book;
import com.kodilla.kodillalibrary.domain.Copy;
import com.kodilla.kodillalibrary.repository.BookRepository;
import com.kodilla.kodillalibrary.repository.CopyRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    private final CopyRepository copyRepository;

    @Autowired
    public BookService(BookRepository bookRepository, CopyRepository copyRepository) {
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
    }

    @Transactional
    public Book saveBook(final Book book) {
        return bookRepository.save(book);
    }

    public Book getBookById(final Long bookId) throws BookNotFoundException {
        return bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
    }

    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    @Transactional
    public void deleteBookById(final Long bookId) throws BookNotFoundException, CopiesNotReturnedException {

        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        List<Copy> copies = book.getCopies();

        Iterator<Copy> iterator = copies.iterator();
        while (iterator.hasNext()) {
            Copy copy = iterator.next();
            if ("available".equals(copy.getStatus())) {
                iterator.remove();
                copyRepository.deleteById(copy.getCopyId());
            }
        }
        if (copies.isEmpty()) {
            bookRepository.deleteById(bookId);
        } else {
            throw new CopiesNotReturnedException();
        }

    }

    public int getAvailableCopies(final Long bookId) {
        return bookRepository.countAvailableCopies(bookId);
    }

}