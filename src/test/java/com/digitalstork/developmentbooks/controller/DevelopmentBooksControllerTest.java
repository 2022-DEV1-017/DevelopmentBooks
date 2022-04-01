package com.digitalstork.developmentbooks.controller;

import com.digitalstork.developmentbooks.dto.BasketDto;
import com.digitalstork.developmentbooks.dto.BasketPriceDto;
import com.digitalstork.developmentbooks.dto.BookDto;
import com.digitalstork.developmentbooks.dto.DiscountDto;
import com.digitalstork.developmentbooks.exceptions.ApiError;
import com.digitalstork.developmentbooks.exceptions.ErrorCode;
import com.digitalstork.developmentbooks.exceptions.UnavailableBookException;
import com.digitalstork.developmentbooks.service.DevelopmentBooksService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DevelopmentBooksControllerTest {

    @InjectMocks
    private DevelopmentBooksController developmentBooksController;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

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

    @Test
    void should_return_ok_and_price_details_when_calling_calculate_price_endpoint_with_correct_body() {
        // Given
        String url = "http://localhost:" + port + "/api/development-books/calculate-price";
        BasketDto basket = BasketDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("1", 1);
                }})
                .build();

        BasketPriceDto basketPrice = BasketPriceDto.builder()
                .totalPrice(50.0)
                .build();

        // When
        when(developmentBooksService.calculatePrice(any(BasketDto.class)))
                .thenReturn(basketPrice);

        ResponseEntity<BasketPriceDto> response = restTemplate.postForEntity(url, basket, BasketPriceDto.class);

        // Test Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(50.0, response.getBody().getTotalPrice());
    }


    @Test
    void should_return_bad_request_https_status_when_calling_calculate_price_endpoint_with_null_book_quantities() {
        // Given
        String url = "http://localhost:" + port + "/api/development-books/calculate-price";
        BasketDto basket = BasketDto.builder()
                .build();

        // When
        ResponseEntity<ApiError> response = restTemplate.postForEntity(url, basket, ApiError.class);

        // Test Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorCode.DATA_VALIDATION.name(), response.getBody().getErrorCode());

        List<String> subErrors = response.getBody().getSubErrors();

        assertEquals(1, subErrors.size());
        assertEquals("bookQuantities must be provided !", subErrors.get(0));
    }

    @Test
    void should_return_bad_request_https_status_when_calling_calculate_price_endpoint_with_unavailable_book() {
        // Given
        String url = "http://localhost:" + port + "/api/development-books/calculate-price";
        BasketDto basket = BasketDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("7", 1);
                }})
                .build();

        // When
        when(developmentBooksService.calculatePrice(any(BasketDto.class))).thenThrow(new UnavailableBookException("7"));
        ResponseEntity<ApiError> response = restTemplate.postForEntity(url, basket, ApiError.class);

        // Test Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorCode.RESOURCE_NOT_FOUND.name(), response.getBody().getErrorCode());
        assertEquals("The following book is not available : 7", response.getBody().getMessage());
    }
}
