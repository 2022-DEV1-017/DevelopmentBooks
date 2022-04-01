package com.digitalstork.bookskata.service;

import com.digitalstork.bookskata.dto.BooksCartDto;
import com.digitalstork.bookskata.dto.BooksCartPriceDto;


public interface BooksCartService {

    BooksCartPriceDto calculatePrice(BooksCartDto cart);

}
