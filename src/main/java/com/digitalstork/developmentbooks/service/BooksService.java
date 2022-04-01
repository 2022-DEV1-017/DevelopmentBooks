package com.digitalstork.developmentbooks.service;

import com.digitalstork.developmentbooks.dto.BookDto;
import org.springframework.stereotype.Service;

@Service
public class BooksService implements IBooksService {

    @Override
    public BookDto getBook(String externalKey) {
        return null;
    }

}
