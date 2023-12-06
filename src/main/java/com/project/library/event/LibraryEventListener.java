package com.project.library.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.library.dto.BookBorrowEventDTO;
import com.project.library.dto.BookReturnEventDTO;
import com.project.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Event listener for library events.
 * 2 topics for book borrow and return.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class LibraryEventListener {

    private final ObjectMapper objectMapper;

    private final LibraryService libraryService;

    @JmsListener(destination = "book.borrow.topic")
    public void handleBorrowEvent(Message message) {
        try {
            String text = ((javax.jms.TextMessage) message).getText();
            String jsonString = turnMessageToJson(text);
            BookBorrowEventDTO bookBorrowEventDTO = objectMapper.readValue(jsonString, BookBorrowEventDTO.class);
            libraryService.processBorrowEvent(bookBorrowEventDTO);
            log.info("Book borrow event handled. User: " + bookBorrowEventDTO.getUserId() + " borrowed book ID: " + bookBorrowEventDTO.getBookId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unknown error while handling book borrow event.");
        }
    }


    @JmsListener(destination = "book.return.topic")
    public void handleReturnEvent(Message message) {
        try {
            String text = ((javax.jms.TextMessage) message).getText();
            String jsonString = turnMessageToJson(text);
            BookReturnEventDTO bookReturnEventDTO = objectMapper.readValue(jsonString, BookReturnEventDTO.class);
            libraryService.processReturnEvent(bookReturnEventDTO);
            log.info("Book: " + bookReturnEventDTO.getBookId() + " is returned and now it's available.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unknown error while handling book return event.");
        }
    }

    private String turnMessageToJson(String input) throws JsonProcessingException {
        String[] parts = input.split("\\(|\\)");
        Map<String, String> keyValuePairs = Arrays.stream(parts[1].split(","))
                .map(pair -> pair.split("="))
                .collect(Collectors.toMap(pair -> pair[0].trim(), pair -> pair[1].trim()));
        return new ObjectMapper().writeValueAsString(keyValuePairs);
    }
}