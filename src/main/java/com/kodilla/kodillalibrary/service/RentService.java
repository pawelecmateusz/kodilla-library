package com.kodilla.kodillalibrary.service;

import com.kodilla.kodillalibrary.controller.exceptions.*;
import com.kodilla.kodillalibrary.domain.Copy;
import com.kodilla.kodillalibrary.domain.Reader;
import com.kodilla.kodillalibrary.domain.Rent;
import com.kodilla.kodillalibrary.repository.RentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class RentService {

    private final RentRepository rentRepository;

    private final CopyService copyService;

    @Autowired
    public RentService(RentRepository rentRepository, CopyService copyService) {
        this.rentRepository = rentRepository;
        this.copyService = copyService;
    }

    @Transactional
    public Rent saveRent(final Rent rent) {
        return rentRepository.save(rent);
    }

    public Rent getRentById(final Long rentId) throws RentNotFoundException {
        return rentRepository.findById(rentId).orElseThrow(RentNotFoundException::new);
    }

    public List<Rent> getAllRents() {
        return (List<Rent>) rentRepository.findAll();
    }

    @Transactional
    public void deleteRentById(final Long rentId) {
        rentRepository.deleteById(rentId);
    }

    @Transactional
    public void rentABook(Copy copy, Reader reader) throws CopyNotFoundException, CopyCurrentlyRentedException {

        if (copyService.getCopyById(copy.getCopyId()).getStatus().isEmpty()) {
            throw new CopyNotFoundException();
        } else if (copyService.getCopyById(copy.getCopyId()).getStatus().equals("rented")) {
            throw new CopyCurrentlyRentedException();
        } else {
            copyService.updateCopyStatus(copy.getCopyId(), "rented");
            Rent rent = Rent.builder()
                    .copy(copyService.getCopyById(copy.getCopyId()))
                    .reader(reader)
                    .rentDate(LocalDate.now())
                    .build();
            rentRepository.save(rent);
        }
    }

    @Transactional
    public void returnABook(Long rentId) throws RentNotFoundException, BookAlreadyReturnedException {

        if (rentRepository.findById(rentId).isEmpty()) {
            throw new RentNotFoundException();
        } else if (rentRepository.findById(rentId).get().getCopy().getStatus().equals("available")) {
            throw new BookAlreadyReturnedException();
        } else {
            Rent returnedBook = rentRepository.findById(rentId).orElseThrow(RentNotFoundException::new);
            returnedBook.getCopy().getRent().setReturnDate(LocalDate.now());
            returnedBook.getCopy().setStatus("available");
            returnedBook.getReader().getRents().remove(returnedBook);
            rentRepository.save(returnedBook);
        }
    }

}