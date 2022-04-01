package com.digitalstork.bookskata.service;

import com.digitalstork.bookskata.dto.BookDto;

import java.util.Collection;

public interface BooksService {

    BookDto getBook(String externalCode);

    Collection<BookDto> getAllBooks();

}
