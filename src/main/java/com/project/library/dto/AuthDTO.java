package com.project.library.dto;

import lombok.*;

/**
 * Data transfer object for authorization response.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthDTO {
    private String accessToken;
}
