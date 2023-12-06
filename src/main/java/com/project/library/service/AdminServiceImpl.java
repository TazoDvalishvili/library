package com.project.library.service;

import com.project.library.dto.BookDTO;
import com.project.library.model.Book;
import com.project.library.repository.BookRepository;
import com.project.library.utils.Constants;
import com.sun.jdi.InternalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of AdminService
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final BookRepository bookRepository;

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        try {
            Book book = new Book();
            book.setAuthor(bookDTO.getAuthor());
            book.setIsbn(bookDTO.getIsbn());
            book.setTitle(bookDTO.getTitle());
            book.setStatus(Constants.BOOK_AVAILABLE);
            book = bookRepository.save(book);
            return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), bookDTO.getIsbn(), bookDTO.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Unknown error while adding book.");
        }
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) throws IllegalAccessException {
        try {
            Optional<Book> optionalBook = bookRepository.findById(id);
            if(optionalBook.isEmpty()) {
                log.error("Book with provided id doesn't exist.");
                throw new IllegalAccessException("Book with provided id doesn't exist.");
            }
            Book book = optionalBook.get();
            book.setAuthor(bookDTO.getAuthor());
            book.setIsbn(bookDTO.getIsbn());
            book.setTitle(bookDTO.getTitle());
            book = bookRepository.save(book);
            return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), bookDTO.getIsbn(), bookDTO.getStatus());
        } catch (Exception e) {
            log.error("Unknown error while updating book.");
            e.printStackTrace();
            if(e instanceof IllegalAccessException) {
                throw e;
            }
            throw new InternalException("Unknown error while updating book.");
        }
    }

    @Override
    public void removeBook(Long id) {
        try {
            if(bookRepository.existsById(id)) {
                bookRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException("Unknown error while deleting book.");
        }
    }
}
