package com.kodilla.kodillalibrary.controller;

import com.kodilla.kodillalibrary.controller.exceptions.BookNotFoundException;
import com.kodilla.kodillalibrary.controller.exceptions.CopyCurrentlyRentedException;
import com.kodilla.kodillalibrary.controller.exceptions.CopyNotFoundException;
import com.kodilla.kodillalibrary.domain.Copy;
import com.kodilla.kodillalibrary.controller.dto.CopyDto;
import com.kodilla.kodillalibrary.mapper.CopyMapper;
import com.kodilla.kodillalibrary.service.CopyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/copies")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CopyController {

    private final CopyMapper copyMapper;
    private final CopyService copyService;

    @GetMapping(value = "{copyId}")
    public ResponseEntity<CopyDto> getCopyById(@PathVariable Long copyId) throws CopyNotFoundException {
        return ResponseEntity.ok(copyMapper.mapToCopyDto(copyService.getCopyById(copyId)));
    }

    @GetMapping
    public ResponseEntity<List<CopyDto>> getAllCopies() {
        return ResponseEntity.ok(copyMapper.mapToCopyDtoList(copyService.getAllCopies()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CopyDto> createCopy(@RequestBody CopyDto copyDto) throws BookNotFoundException {
        Copy copy = copyMapper.mapToCopy(copyDto);
        copyService.saveCopy(copy);
        return ResponseEntity.ok().build();
    }
    @PutMapping
    public ResponseEntity<CopyDto> updateCopy(@RequestBody CopyDto copyDto) throws BookNotFoundException {
        Copy copy = copyMapper.mapToCopy(copyDto);
        Copy savedCopy = copyService.saveCopy(copy);
        return ResponseEntity.ok(copyMapper.mapToCopyDto(savedCopy));
    }

    @DeleteMapping(value = "{copyId}")
    public ResponseEntity<Void> deleteCopy(@PathVariable Long copyId) throws CopyNotFoundException, CopyCurrentlyRentedException {
        copyService.deleteCopyById(copyId);
        return ResponseEntity.ok().build();
    }

}