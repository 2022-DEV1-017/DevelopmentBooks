package com.digitalstork.developmentbooks.service;

import com.digitalstork.developmentbooks.domain.Book;
import com.digitalstork.developmentbooks.dto.BookDto;
import com.digitalstork.developmentbooks.exceptions.UnavailableBookException;
import com.digitalstork.developmentbooks.mapper.BookMapper;
import com.digitalstork.developmentbooks.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BooksService implements IBooksService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper = new BookMapper();

    public BooksService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto getBook(String externalKey) {
        Book book = bookRepository.findBookByExternalCode(externalKey).orElseThrow(() -> new UnavailableBookException(externalKey));
        return bookMapper.apply(book);
    }

}
