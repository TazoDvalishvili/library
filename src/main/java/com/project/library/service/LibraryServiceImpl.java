package com.project.library.service;


import com.project.library.dto.BookBorrowEventDTO;
import com.project.library.dto.BookDTO;
import com.project.library.dto.BookReturnEventDTO;
import com.project.library.event.LibraryEventProducer;
import com.project.library.model.Book;
import com.project.library.model.User;
import com.project.library.repository.BookRepository;
import com.project.library.repository.UserRepository;
import com.project.library.utils.Constants;
import com.sun.jdi.InternalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implements library services.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService{
    private final LibraryEventProducer libraryEventProducer;

    private final UserService userService;

    private final UserRepository userRepository;

    private final BookRepository bookRepository;


    @Override
    public List<BookDTO> borrowBook(Long id) {
        try {
            if(!validateBookBorrowEvent(id)) {
                throw new InternalException("Book is not available!");
            }
            User user = userService.getCurrentUser();
            libraryEventProducer.sendBorrowEvent(id, user.getId());
            return user.getBorrowedBooks().stream().map(book -> new BookDTO(book.getId(),
                    book.getTitle(), book.getAuthor(), book.getIsbn(), book.getStatus())).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof InternalException) {
                throw e;
            }
            throw new RuntimeException("Cannot borrow book");
        }
    }


    @Override
    public List<BookDTO> returnBook(Long id) {
        try {
            User user = userService.getCurrentUser();
            libraryEventProducer.sendReturnEvent(id);
            return user.getBorrowedBooks().stream().map(book -> new BookDTO(book.getId(),
                    book.getTitle(), book.getAuthor(), book.getIsbn(), book.getStatus())).collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalException("Cannot return book");
        }
    }

    @Override
    public void processBorrowEvent(BookBorrowEventDTO bookBorrowEventDTO) {
        try {
            Book book = bookRepository.findById(bookBorrowEventDTO.getBookId()).get();
            book.setStatus(Constants.BOOK_UNAVAILABLE);
            book.setUser(userRepository.findById(bookBorrowEventDTO.getUserId()).get());
            bookRepository.save(book);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Unknown error while processing book borrow event");
            if(e instanceof InternalException) {
                throw e;
            }
            throw new RuntimeException("Unknown error while processing book borrow event");
        }
    }

    private boolean validateBookBorrowEvent(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isEmpty()) {
            return false;
        }
        return !bookOptional.get().getStatus().equals(Constants.BOOK_UNAVAILABLE);
    }

    @Override
    public void processReturnEvent(BookReturnEventDTO bookReturnEventDTO) {
        try {
            Book book = bookRepository.findById(bookReturnEventDTO.getBookId()).get();
            book.setStatus(Constants.BOOK_AVAILABLE);
            book.setUser(null);
            bookRepository.save(book);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Unknown error while processing book return event");
            if(e instanceof InternalException) {
                throw e;
            }
            throw new RuntimeException("Unknown error while processing book return event");
        }
    }
}
