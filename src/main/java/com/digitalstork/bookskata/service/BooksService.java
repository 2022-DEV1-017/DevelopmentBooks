package com.digitalstork.bookskata.service;

import com.digitalstork.bookskata.dto.BookDto;

public interface BooksService {

    BookDto getBook(String externalCode);

}
