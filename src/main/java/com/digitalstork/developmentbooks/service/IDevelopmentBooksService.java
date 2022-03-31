package com.digitalstork.developmentbooks.service;

import com.digitalstork.developmentbooks.dto.BasketDto;
import com.digitalstork.developmentbooks.dto.BasketPriceDto;


public interface IDevelopmentBooksService {

    BasketPriceDto calculatePrice(BasketDto basket);

}
