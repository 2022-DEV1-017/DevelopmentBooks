package com.digitalstork.developmentbooks.controller;

import com.digitalstork.developmentbooks.dto.BasketDto;
import com.digitalstork.developmentbooks.dto.BasketPriceDto;
import com.digitalstork.developmentbooks.dto.BookDto;
import com.digitalstork.developmentbooks.dto.DiscountDto;
import com.digitalstork.developmentbooks.service.DevelopmentBooksService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DevelopmentBooksControllerTest {

    @InjectMocks
    private DevelopmentBooksController developmentBooksController;

    @Mock
    private DevelopmentBooksService developmentBooksService;

    @Test
    void should_return_development_book_service_value() {
        // Given
        BasketDto basket = BasketDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("1", 1);
                }})
                .build();

        BookDto book = BookDto.builder()
                .externalCode("1")
                .author("Robert Martin")
                .name("Clean Code")
                .year(2008)
                .build();

        DiscountDto discount = DiscountDto.builder()
                .books(Collections.singleton(book))
                .rate(0.0)
                .unitPrice(50.0)
                .build();

        BasketPriceDto basketPrice = BasketPriceDto.builder()
                .totalPrice(50.0)
                .discounts(Collections.singleton(discount))
                .build();

        // When
        when(developmentBooksService.calculatePrice(any(BasketDto.class))).thenReturn(basketPrice);
        ResponseEntity<BasketPriceDto> response = developmentBooksController.calculatePrice(basket);

        // Assertions
        verify(developmentBooksService).calculatePrice(any(BasketDto.class));
        assertNotNull(response);
        assertEquals(basketPrice, response.getBody());
    }
}
