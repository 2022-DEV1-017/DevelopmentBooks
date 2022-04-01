package com.digitalstork.developmentbooks.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountDto {

    @Schema(description = "Books on which is applied this discount", minLength = 1)
    private Collection<BookDto> books;

    @Schema(description = "Discount Rate", example = "0.05")
    private Double rate;

    @Schema(description = "Price of one book once the discount is applied", example = "47.5")
    private Double unitPrice;

    @Schema(description = "Number of copies of each one of the books", example = "1")
    private Integer copies;
}
