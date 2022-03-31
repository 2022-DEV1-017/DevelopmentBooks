package com.digitalstork.developmentbooks.dto;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketPriceDto {

    private Collection<DiscountDto> discounts;

    private Double totalPrice;
}
