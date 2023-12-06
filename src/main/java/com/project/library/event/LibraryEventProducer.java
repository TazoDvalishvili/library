package com.project.library.event;

import com.project.library.dto.BookBorrowEventDTO;
import com.project.library.dto.BookReturnEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * Produces library events and sends them to topics.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class LibraryEventProducer {

    private final JmsTemplate jmsTemplate;

    public void sendBorrowEvent(Long bookId, Long userId) {
        try {
            jmsTemplate.convertAndSend("book.borrow.topic", new BookBorrowEventDTO(bookId, userId).toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while sending borrow event");
        }
    }

    public void sendReturnEvent(Long bookId) {
        try {
            jmsTemplate.convertAndSend("book.return.topic", new BookReturnEventDTO(bookId).toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while sending return event");
        }

    }
}
