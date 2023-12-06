package com.project.library.service;

import com.project.library.dto.BookBorrowEventDTO;
import com.project.library.dto.BookDTO;
import com.project.library.dto.BookReturnEventDTO;

import java.util.List;

/**
 * General library services.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

public interface LibraryService {
    /*
    * User borrows book. User's current books list is returned.
     */
    List<BookDTO> borrowBook(Long id);

    /*
     * User returns book. User's current books list is returned.
     */
    List<BookDTO> returnBook(Long id);

    /*
    * Processes book borrow event.
     */
    void processBorrowEvent(BookBorrowEventDTO bookBorrowEventDTO);

    /*
    * Processes book return event.
     */
    void processReturnEvent(BookReturnEventDTO bookReturnEventDTO);
}
