package com.digitalstork.developmentbooks.dto;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountDto {

    private Collection<BookDto> books;

    private Float rate;

    private Double unitPrice;
}
