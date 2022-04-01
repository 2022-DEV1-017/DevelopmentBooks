package com.digitalstork.developmentbooks.mapper;

import com.digitalstork.developmentbooks.domain.Book;
import com.digitalstork.developmentbooks.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class BookMapperTest {

    @InjectMocks
    private BookMapper bookMapper;

    @Test
    void should_map_Book_to_BookDto() {
        // Given
        Book bookEntity = Book.builder()
                .id(1L)
                .externalCode("1")
                .name("Clean Code")
                .author("Robert Martin")
                .year(2008)
                .build();

        // When
        BookDto bookDto = bookMapper.apply(bookEntity);

        // Assertions
        assertNotNull(bookDto);
        assertEquals("1", bookDto.getExternalCode());
        assertEquals("Clean Code", bookDto.getName());
        assertEquals("Robert Martin", bookDto.getAuthor());
        assertEquals(2008, bookDto.getYear());
    }
}
