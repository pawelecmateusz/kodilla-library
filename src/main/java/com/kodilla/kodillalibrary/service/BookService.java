package com.kodilla.kodillalibrary.service;

import com.kodilla.kodillalibrary.controller.exceptions.BookNotFoundException;
import com.kodilla.kodillalibrary.controller.exceptions.CopiesNotReturnedException;
import com.kodilla.kodillalibrary.domain.Book;
import com.kodilla.kodillalibrary.domain.Copy;
import com.kodilla.kodillalibrary.repository.BookRepository;
import com.kodilla.kodillalibrary.repository.CopyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CopyRepository copyRepository;

    public Book saveBook(final Book book) {
        return bookRepository.save(book);
    }

    public Book getBookById(final Long bookId) throws BookNotFoundException {
        return bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void deleteBookById(final Long bookId) throws BookNotFoundException {

        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        List<Copy> copies = book.getCopies();

        Iterator<Copy> iterator = copies.iterator();
        while (iterator.hasNext()) {
            Copy copy = iterator.next();
            if (copy.getStatus().equals("available")) {
                iterator.remove();
                copyRepository.deleteById(copy.getCopyId());
            }
        }
        try {
            if (copies.isEmpty()) {
                bookRepository.deleteById(bookId);
            } else {
                throw new CopiesNotReturnedException();
            }
        } catch (CopiesNotReturnedException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getAvailableCopies(final Long bookId) {
        return bookRepository.countAvailableCopies(bookId);
    }

}