package com.kodilla.kodillalibrary.service;

import com.kodilla.kodillalibrary.controller.exceptions.CopyCurrentlyRented;
import com.kodilla.kodillalibrary.controller.exceptions.CopyNotFoundException;
import com.kodilla.kodillalibrary.domain.Book;
import com.kodilla.kodillalibrary.domain.Copy;
import com.kodilla.kodillalibrary.repository.CopyRepository;
import com.kodilla.kodillalibrary.repository.RentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CopyService {

    @Autowired
    private final CopyRepository copyRepository;

    @Autowired
    private final RentRepository rentRepository;

    public Copy saveCopy(final Copy copy) {
        return copyRepository.save(copy);
    }

    public Copy getCopyById(final Long copyId) {
        try {
            return copyRepository.findById(copyId).orElseThrow(CopyNotFoundException::new);
        } catch (CopyNotFoundException e) {
            System.out.println(e.getMessage());
            return Copy.builder()
                    .book(Book.builder().build())
                    .build();
        }
    }

    public List<Copy> getAllCopies() {
        return copyRepository.findAll();
    }

    public void deleteCopyById(final Long copyId) {
            try {
                Copy copy = copyRepository.findById(copyId).orElseThrow(CopyNotFoundException::new);
                if (copy.getRent() != null) {
                    if (copy.getStatus().equals("available")) {
                        Long rent = copy.getRent().getRentId();
                        copy.setRent(null);
                        copy.getBook().getCopies().remove(copy);
                        rentRepository.deleteById(rent);
                        copyRepository.deleteById(copy.getCopyId());
                    } else {
                        throw new CopyCurrentlyRented();
                    }
                } else {
                    if (copy.getStatus().equals("available")) {
                        copyRepository.deleteById(copy.getCopyId());
                    }
                }
            } catch (CopyCurrentlyRented | CopyNotFoundException e) {
                System.out.println(e.getMessage());
            }
    }

    public void updateCopyStatus(Long copyId, String rented) {
        try {
            Copy copy = copyRepository.findById(copyId).orElseThrow(CopyNotFoundException::new);
            copy.setStatus(rented);
            copyRepository.save(copy);
        } catch (CopyNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}