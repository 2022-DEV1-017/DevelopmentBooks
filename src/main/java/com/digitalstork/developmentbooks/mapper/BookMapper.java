package com.digitalstork.developmentbooks.mapper;

import com.digitalstork.developmentbooks.domain.Book;
import com.digitalstork.developmentbooks.dto.BookDto;

import java.util.function.Function;

public class BookMapper implements Function<Book, BookDto> {

    @Override
    public BookDto apply(Book book) {
        return null;
    }
}
