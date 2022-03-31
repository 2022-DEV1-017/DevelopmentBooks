package com.digitalstork.developmentbooks.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {

    private String externalCode;
    private String name;
    private String author;
    private Integer year;

}
