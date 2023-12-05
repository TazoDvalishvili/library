package com.project.library.dto;

import lombok.*;

import java.util.List;

/**
 * Data transfer object for User.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private List<BookDTO> borrowedBooks;
}
