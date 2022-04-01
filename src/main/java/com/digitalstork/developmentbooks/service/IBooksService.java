package com.digitalstork.developmentbooks.service;

import com.digitalstork.developmentbooks.dto.BookDto;

public interface IBooksService {

    BookDto getBook(String externalCode);

}
