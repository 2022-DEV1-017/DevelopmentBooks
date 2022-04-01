package com.digitalstork.bookskata.service;

import com.digitalstork.bookskata.domain.Book;
import com.digitalstork.bookskata.dto.BookDto;
import com.digitalstork.bookskata.exceptions.UnavailableBookException;
import com.digitalstork.bookskata.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BooksServiceTest {

    @InjectMocks
    private BooksServiceImpl booksServiceImpl;

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
        BookDto bookDto = booksServiceImpl.getBook("1");

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
        UnavailableBookException illegalScoreException = assertThrows(UnavailableBookException.class, () -> booksServiceImpl.getBook("6"));

        assertEquals("The following book is not available : 6", illegalScoreException.getMessage());
    }

    @Test
    void should_return_all_available_books() {
        // Given
        List<Book> allBookEntities = Arrays.asList(
                Book.builder()
                        .id(1L)
                        .externalCode("1")
                        .name("Clean Code")
                        .author("Robert Martin")
                        .year(2008)
                        .build(),
                Book.builder()
                        .id(3L)
                        .externalCode("3")
                        .name("Test Driven Development by Example")
                        .author("Kent Beck")
                        .year(2003)
                        .build()
        );

        // When
        when(bookRepository.findAll()).thenReturn(allBookEntities);
        Collection<BookDto> allBookDtos = booksServiceImpl.getAllBooks();

        // Assertions
        assertNotNull(allBookDtos);
        assertEquals(allBookEntities.size(), allBookDtos.size());

        Book bookEntity = allBookEntities.stream().filter(i -> "1".equals(i.getExternalCode())).findAny().orElseThrow();
        BookDto bookDto = allBookDtos.stream().filter(i -> "1".equals(i.getExternalCode())).findAny().orElseThrow();
        assertEquals(bookEntity.getExternalCode(), bookDto.getExternalCode());
        assertEquals(bookEntity.getName(), bookDto.getName());
        assertEquals(bookEntity.getAuthor(), bookDto.getAuthor());
        assertEquals(bookEntity.getYear(), bookDto.getYear());

        bookEntity = allBookEntities.stream().filter(i -> "3".equals(i.getExternalCode())).findAny().orElseThrow();
        bookDto = allBookDtos.stream().filter(i -> "3".equals(i.getExternalCode())).findAny().orElseThrow();
        assertEquals(bookEntity.getExternalCode(), bookDto.getExternalCode());
        assertEquals(bookEntity.getName(), bookDto.getName());
        assertEquals(bookEntity.getAuthor(), bookDto.getAuthor());
        assertEquals(bookEntity.getYear(), bookDto.getYear());
    }

}
