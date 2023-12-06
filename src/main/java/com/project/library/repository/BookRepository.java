package com.project.library.repository;

import com.project.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Books.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */
public interface BookRepository extends JpaRepository<Book, Long> {
}
