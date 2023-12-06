package com.project.library.controller;

import com.project.library.dto.BookDTO;
import com.project.library.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Admin controller managing crud operations for admin.
 * These endpoints are secured and only Admin role is permitted to execute them.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

@RestController
@Slf4j
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("add-book")
    public ResponseEntity<?> addBook(@RequestBody BookDTO bookDTO) {
        try {
            return new ResponseEntity<>(adminService.addBook(bookDTO), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("update-book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        try {
            return new ResponseEntity<>(adminService.updateBook(id, bookDTO), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("remove-book/{id}")
    private ResponseEntity<?> removeBook(@PathVariable Long id) {
        try {
            adminService.removeBook(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
