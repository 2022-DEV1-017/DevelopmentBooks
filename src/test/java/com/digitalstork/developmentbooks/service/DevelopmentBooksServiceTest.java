package com.digitalstork.developmentbooks.service;

import com.digitalstork.developmentbooks.dto.BasketDto;
import com.digitalstork.developmentbooks.dto.BasketPriceDto;
import com.digitalstork.developmentbooks.dto.DiscountDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class DevelopmentBooksServiceTest {

    @InjectMocks
    private DevelopmentBooksService developmentBooksService;

    @Test
    void should_not_apply_discount_if_one_copy_only() {
        // Given
        BasketDto basket = BasketDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("1", 1);
                }})
                .build();

        // When
        BasketPriceDto basketPrice = developmentBooksService.calculatePrice(basket);

        // Assertions
        assertNotNull(basketPrice);
        assertNotNull(basketPrice.getDiscounts());
        assertEquals(1, basketPrice.getDiscounts().size());
        DiscountDto discount = basketPrice.getDiscounts().stream().findAny().orElseThrow();
        assertEquals(0, discount.getRate());
        assertEquals(IDevelopmentBooksService.BOOK_PRICE, discount.getUnitPrice());
    }
}
