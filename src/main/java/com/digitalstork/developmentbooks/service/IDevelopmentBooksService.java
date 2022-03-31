package com.digitalstork.developmentbooks.service;

import com.digitalstork.developmentbooks.dto.BasketDto;
import com.digitalstork.developmentbooks.dto.BasketPriceDto;


public interface IDevelopmentBooksService {

    Double BOOK_PRICE = 50.0;

    BasketPriceDto calculatePrice(BasketDto basket);

}
