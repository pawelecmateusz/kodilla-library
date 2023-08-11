package com.kodilla.kodillalibrary.service;

import com.kodilla.kodillalibrary.controller.exceptions.*;
import com.kodilla.kodillalibrary.domain.Copy;
import com.kodilla.kodillalibrary.domain.Reader;
import com.kodilla.kodillalibrary.domain.Rent;
import com.kodilla.kodillalibrary.repository.RentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class RentService {

    private final RentRepository rentRepository;

    private final CopyService copyService;

    public Rent saveRent(final Rent rent) {
        return rentRepository.save(rent);
    }

    public Rent getRentById(final Long rentId) throws RentNotFoundException {
        return rentRepository.findById(rentId).orElseThrow(RentNotFoundException::new);
    }

    public List<Rent> getAllRents() {
        return rentRepository.findAll();
    }

    public void deleteRentById(final Long rentId) {
        rentRepository.deleteById(rentId);
    }

    public void rentABook(Copy copy, Reader reader) {
        try {
            if (copyService.getCopyById(copy.getCopyId()).getStatus().equals("rented")) {
                throw new CopyCurrentlyRented();
            } else {
                copyService.updateCopyStatus(copy.getCopyId(), "rented");
                Rent rent = Rent.builder()
                        .copy(copyService.getCopyById(copy.getCopyId()))
                        .reader(reader)
                        .rentDate(LocalDate.now())
                        .build();
                rentRepository.save(rent);
            }
        } catch (CopyCurrentlyRented | CopyNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void returnABook(Long rentId) throws RentNotFoundException, BookAlreadyReturned {
        if (rentRepository.findById(rentId).get().getCopy().getStatus().equals("available")) {
            throw new BookAlreadyReturned();
        } else {
            Rent returnedBook = rentRepository.findById(rentId).orElseThrow(RentNotFoundException::new);
            returnedBook.getCopy().getRent().setReturnDate(LocalDate.now());
            returnedBook.getCopy().setStatus("available");
            returnedBook.getReader().getRents().remove(returnedBook);
            rentRepository.save(returnedBook);
        }
    }

}