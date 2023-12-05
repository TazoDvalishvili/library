package com.project.library.dto;


import lombok.*;

/**
 * Data transfer object for Book.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer status;
}
