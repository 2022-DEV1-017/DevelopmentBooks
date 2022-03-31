package com.digitalstork.developmentbooks.service;

import com.digitalstork.developmentbooks.dto.BasketDto;
import com.digitalstork.developmentbooks.dto.BasketPriceDto;
import com.digitalstork.developmentbooks.dto.DiscountDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static com.digitalstork.developmentbooks.constants.DevelopmentBooksConstants.*;
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
        assertEquals(NO_DISCOUNT, discount.getRate());
        assertEquals(BOOK_PRICE * (1 - discount.getRate()), discount.getUnitPrice());
    }

    @Test
    void should_apply_5_percent_discount_if_2_different_books() {
        // Given
        BasketDto basket = BasketDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("1", 1);
                    put("2", 1);
                }})
                .build();

        // When
        BasketPriceDto basketPrice = developmentBooksService.calculatePrice(basket);

        // Assertions
        assertNotNull(basketPrice);
        assertNotNull(basketPrice.getDiscounts());
        assertEquals(1, basketPrice.getDiscounts().size());

        DiscountDto discount = basketPrice.getDiscounts().stream().findAny().orElseThrow();

        assertEquals(TWO_DIFFERENT_BOOKS_DISCOUNT, discount.getRate());
        assertEquals(BOOK_PRICE * (1 - discount.getRate()), discount.getUnitPrice());
    }

    @Test
    void should_apply_10_percent_discount_if_3_different_books() {
        // Given
        BasketDto basket = BasketDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("1", 1);
                    put("2", 1);
                    put("3", 1);
                }})
                .build();

        // When
        BasketPriceDto basketPrice = developmentBooksService.calculatePrice(basket);

        // Assertions
        assertNotNull(basketPrice);
        assertNotNull(basketPrice.getDiscounts());
        assertEquals(1, basketPrice.getDiscounts().size());

        DiscountDto discount = basketPrice.getDiscounts().stream().findAny().orElseThrow();

        assertEquals(THREE_DIFFERENT_BOOKS_DISCOUNT, discount.getRate());
        assertEquals(BOOK_PRICE * (1 - discount.getRate()), discount.getUnitPrice());
    }

    @Test
    void should_apply_20_percent_discount_if_4_different_books() {
        // Given
        BasketDto basket = BasketDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("1", 1);
                    put("2", 1);
                    put("3", 1);
                    put("4", 1);
                }})
                .build();

        // When
        BasketPriceDto basketPrice = developmentBooksService.calculatePrice(basket);

        // Assertions
        assertNotNull(basketPrice);
        assertNotNull(basketPrice.getDiscounts());
        assertEquals(1, basketPrice.getDiscounts().size());

        DiscountDto discount = basketPrice.getDiscounts().stream().findAny().orElseThrow();

        assertEquals(FOUR_DIFFERENT_BOOKS_DISCOUNT, discount.getRate());
        assertEquals(BOOK_PRICE * (1 - discount.getRate()), discount.getUnitPrice());
    }

    @Test
    void should_apply_25_percent_discount_if_5_different_books() {
        // Given
        BasketDto basket = BasketDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("1", 1);
                    put("2", 1);
                    put("3", 1);
                    put("4", 1);
                    put("5", 1);
                }})
                .build();

        // When
        BasketPriceDto basketPrice = developmentBooksService.calculatePrice(basket);

        // Assertions
        assertNotNull(basketPrice);
        assertNotNull(basketPrice.getDiscounts());
        assertEquals(1, basketPrice.getDiscounts().size());

        DiscountDto discount = basketPrice.getDiscounts().stream().findAny().orElseThrow();

        assertEquals(FIVE_DIFFERENT_BOOKS_DISCOUNT, discount.getRate());
        assertEquals(BOOK_PRICE * (1 - discount.getRate()), discount.getUnitPrice());
    }
}
