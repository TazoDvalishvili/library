package com.project.library.controller;

import com.project.library.dto.BookDTO;
import com.project.library.service.LibraryService;
import com.project.library.service.RateLimiterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Library: managing user's integration with library services.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

@RestController
@Slf4j
@RequestMapping("library")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

    private final RateLimiterService rateLimiterService;

    @PostMapping("borrow-book/{id}")
    public ResponseEntity<?> borrowBook(@PathVariable Long id) {
        if (!rateLimiterService.tryConsume()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests. Please try again later.");
        }
        try {
            return new ResponseEntity<>(libraryService.borrowBook(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("return-book/{id}")
    public ResponseEntity<?> returnBook(@PathVariable Long id) {
        if (!rateLimiterService.tryConsume()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests. Please try again later.");
        }
        try {
            return new ResponseEntity<>(libraryService.returnBook(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
