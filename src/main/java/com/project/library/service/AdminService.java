package com.project.library.service;

import com.project.library.dto.BookDTO;

/**
 * Admin service for crud operations.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

public interface AdminService {
    BookDTO addBook(BookDTO bookDTO);
    BookDTO updateBook(Long id, BookDTO bookDTO) throws IllegalAccessException;
    void removeBook(Long id);
}
