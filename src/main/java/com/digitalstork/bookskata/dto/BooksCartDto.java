package com.digitalstork.bookskata.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BooksCartDto {

    @Schema(description = "Number of copies of each Book (externalCode)", required = true, example = "{\"1\": 2, \"2\": 1, \"3\": 1}", minProperties = 1)
    @NotEmpty(message = "bookQuantities must be provided !")
    Map<String, Integer> bookQuantities;
}
