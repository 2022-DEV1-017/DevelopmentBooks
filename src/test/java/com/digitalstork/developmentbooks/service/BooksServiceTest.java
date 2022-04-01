package com.digitalstork.developmentbooks.service;

import com.digitalstork.developmentbooks.domain.Book;
import com.digitalstork.developmentbooks.dto.BookDto;
import com.digitalstork.developmentbooks.exceptions.UnavailableBookException;
import com.digitalstork.developmentbooks.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BooksServiceTest {

    @InjectMocks
    private BooksService booksService;

    @Mock
    private BookRepository bookRepository;

    @Test
    void should_return_book_details_from_externalCode() {
        // Given
        Book book = Book.builder()
                .id(1L)
                .externalCode("1")
                .name("Clean Code")
                .author("Robert Martin")
                .year(2008)
                .build();

        // When
        when(bookRepository.findBookByExternalCode("1")).thenReturn(Optional.of(book));
        BookDto bookDto = booksService.getBook("1");

        // Assertions
        assertNotNull(bookDto);
        assertEquals(book.getExternalCode(), bookDto.getExternalCode());
        assertEquals(book.getName(), bookDto.getName());
        assertEquals(book.getAuthor(), bookDto.getAuthor());
        assertEquals(book.getYear(), bookDto.getYear());
    }

    @Test
    void should_throw_UnavailableBookException_when_bad_book_externalCode() {
        // Given

        // When
        when(bookRepository.findBookByExternalCode(anyString())).thenReturn(Optional.empty());

        // Assertions
        UnavailableBookException illegalScoreException = assertThrows(UnavailableBookException.class, () -> booksService.getBook("6"));

        assertEquals("The following book is not available : 6", illegalScoreException.getMessage());
    }

}
