package com.digitalstork.bookskata.service;

import com.digitalstork.bookskata.domain.Book;
import com.digitalstork.bookskata.dto.BookDto;
import com.digitalstork.bookskata.exceptions.UnavailableBookException;
import com.digitalstork.bookskata.mapper.BookMapper;
import com.digitalstork.bookskata.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BooksServiceImpl implements BooksService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper = new BookMapper();

    public BooksServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto getBook(String externalKey) {
        Book book = bookRepository.findBookByExternalCode(externalKey).orElseThrow(() -> new UnavailableBookException(externalKey));
        return bookMapper.apply(book);
    }

}