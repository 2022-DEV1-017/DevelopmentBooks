package com.digitalstork.developmentbooks.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketPriceDto {

    @Schema(description = "Discounts definition to explain the price", minLength = 1,
            example = "[{ \"books\": [ { \"externalCode\": \"1\", \"name\": \"Clean Code\", \"author\": \"Robert Martin\", \"year\": 2008 }, { \"externalCode\": \"2\", \"name\": \"The Clean Coder\", \"author\": \"Robert Martin\", \"year\": 2011 }, { \"externalCode\": \"3\", \"name\": \"Clean Architecture\", \"author\": \"Robert Martin\", \"year\": 2017 } ], \"rate\": 0.1, \"unitPrice\": 45.0, \"copies\": 1 },{ \"books\": [{ \"externalCode\": \"1\", \"name\": \"Clean Code\", \"author\": \"Robert Martin\", \"year\": 2008 }], \"rate\": 0.0, \"unitPrice\": 50.0, \"copies\": 1 }]")
    private Collection<DiscountDto> discounts;

    @Schema(description = "Total Price including all discounts", example = "185.0")
    private Double totalPrice;
}
