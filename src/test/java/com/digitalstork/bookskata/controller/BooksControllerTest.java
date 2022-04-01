package com.digitalstork.bookskata.controller;

import com.digitalstork.bookskata.dto.BookDto;
import com.digitalstork.bookskata.service.BooksServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BooksControllerTest {

    @InjectMocks
    private BooksController booksController;

    @Mock
    private BooksServiceImpl booksService;

    @Test
    void should_return_books_service_value() {
        // Given
        Collection<BookDto> allBooks = Arrays.asList(
                BookDto.builder()
                        .externalCode("1")
                        .name("Clean Code")
                        .author("Robert Martin")
                        .year(2008)
                        .build(),
                BookDto.builder()
                        .externalCode("2")
                        .name("The Clean Coder")
                        .author("Robert Martin")
                        .year(2011)
                        .build()
        );

        // When
        when(booksService.getAllBooks()).thenReturn(allBooks);
        ResponseEntity<Collection<BookDto>> response = booksController.getAllBooks();

        // Assertions
        verify(booksService).getAllBooks();
        assertNotNull(response);
        assertEquals(allBooks, response.getBody());
    }

}
