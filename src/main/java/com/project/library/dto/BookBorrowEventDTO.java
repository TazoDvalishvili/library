package com.project.library.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookBorrowEventDTO {
    private Long bookId;
    private Long userId;
}
