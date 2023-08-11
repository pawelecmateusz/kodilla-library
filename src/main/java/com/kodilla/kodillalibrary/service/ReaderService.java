package com.kodilla.kodillalibrary.service;

import com.kodilla.kodillalibrary.controller.exceptions.ReaderHasRentsException;
import com.kodilla.kodillalibrary.controller.exceptions.ReaderNotFoundException;
import com.kodilla.kodillalibrary.domain.Reader;
import com.kodilla.kodillalibrary.domain.Rent;
import com.kodilla.kodillalibrary.repository.ReaderRepository;
import com.kodilla.kodillalibrary.repository.RentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ReaderService {

    @Autowired
    private final ReaderRepository readerRepository;

    @Autowired
    private final RentRepository rentRepository;

    public Reader saveReader(final Reader reader) {
        return readerRepository.save(reader);
    }

    public Reader getReaderById(final Long readerId) throws ReaderNotFoundException {
        return readerRepository.findById(readerId).orElseThrow(ReaderNotFoundException::new);
    }

    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    public void deleteReaderById(final Long readerId) throws ReaderNotFoundException, ReaderHasRentsException {
        Reader reader = readerRepository.findById(readerId).orElseThrow(ReaderNotFoundException::new);
        List<Rent> rents = reader.getRents();

        Iterator<Rent> iterator = rents.iterator();
        while (iterator.hasNext()) {
            Rent rent = iterator.next();
            if (rent.getReturnDate() != null) {
                rent.setCopy(null);
                iterator.remove();
                rentRepository.deleteById(rent.getRentId());
            } else {
                throw new ReaderHasRentsException();
            }
        }

        try {
            if (reader.getRents().isEmpty()) {
                readerRepository.deleteById(readerId);
            } else {
                throw new ReaderHasRentsException();
            }
        } catch (ReaderHasRentsException e) {
            System.out.println(e.getMessage());
        }
    }

}