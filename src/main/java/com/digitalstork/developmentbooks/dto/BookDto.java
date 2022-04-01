package com.digitalstork.developmentbooks.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {

    @Schema(description = "External code used to identify the book", example = "1")
    private String externalCode;

    @Schema(description = "Name of the book", example = "Clean Code")
    private String name;

    @Schema(description = "Author of the book", example = "Robert Martin")
    private String author;

    @Schema(description = "Year of the book", example = "2008")
    private Integer year;

    @Override
    public String toString() {
        return externalCode + ". " + name + " (" + author + ", " + year + ")";
    }
}
