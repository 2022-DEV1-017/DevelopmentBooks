package com.digitalstork.bookskata.controller;

import com.digitalstork.bookskata.dto.BookDto;
import com.digitalstork.bookskata.dto.BooksCartDto;
import com.digitalstork.bookskata.dto.BooksCartPriceDto;
import com.digitalstork.bookskata.dto.DiscountDto;
import com.digitalstork.bookskata.exceptions.ApiError;
import com.digitalstork.bookskata.exceptions.ErrorCode;
import com.digitalstork.bookskata.exceptions.UnavailableBookException;
import com.digitalstork.bookskata.service.BooksCartServiceImpl;
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
public class BooksCartControllerTest {

    @InjectMocks
    private BooksCartController booksCartController;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    private BooksCartServiceImpl booksCartService;

    @Test
    void should_return_book_cart_service_value() {
        // Given
        BooksCartDto cart = BooksCartDto.builder()
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

        BooksCartPriceDto cartPrice = BooksCartPriceDto.builder()
                .totalPrice(50.0)
                .discounts(Collections.singleton(discount))
                .build();

        // When
        when(booksCartService.calculatePrice(any(BooksCartDto.class))).thenReturn(cartPrice);
        ResponseEntity<BooksCartPriceDto> response = booksCartController.calculatePrice(cart);

        // Assertions
        verify(booksCartService).calculatePrice(any(BooksCartDto.class));
        assertNotNull(response);
        assertEquals(cartPrice, response.getBody());
    }

    @Test
    void should_return_ok_and_price_details_when_calling_calculate_price_endpoint_with_correct_body() {
        // Given
        String url = "http://localhost:" + port + "/api/books-cart/calculate-price";
        BooksCartDto cart = BooksCartDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("1", 1);
                }})
                .build();

        BooksCartPriceDto cartPrice = BooksCartPriceDto.builder()
                .totalPrice(50.0)
                .build();

        // When
        when(booksCartService.calculatePrice(any(BooksCartDto.class)))
                .thenReturn(cartPrice);

        ResponseEntity<BooksCartPriceDto> response = restTemplate.postForEntity(url, cart, BooksCartPriceDto.class);

        // Test Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(50.0, response.getBody().getTotalPrice());
    }


    @Test
    void should_return_bad_request_https_status_when_calling_calculate_price_endpoint_with_null_book_quantities() {
        // Given
        String url = "http://localhost:" + port + "/api/books-cart/calculate-price";
        BooksCartDto cart = BooksCartDto.builder()
                .build();

        // When
        ResponseEntity<ApiError> response = restTemplate.postForEntity(url, cart, ApiError.class);

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
        String url = "http://localhost:" + port + "/api/books-cart/calculate-price";
        BooksCartDto cart = BooksCartDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("7", 1);
                }})
                .build();

        // When
        when(booksCartService.calculatePrice(any(BooksCartDto.class))).thenThrow(new UnavailableBookException("7"));
        ResponseEntity<ApiError> response = restTemplate.postForEntity(url, cart, ApiError.class);

        // Test Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorCode.RESOURCE_NOT_FOUND.name(), response.getBody().getErrorCode());
        assertEquals("The following book is not available : 7", response.getBody().getMessage());
    }
}
