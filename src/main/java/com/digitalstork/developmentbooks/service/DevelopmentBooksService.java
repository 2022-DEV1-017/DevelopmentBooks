package com.digitalstork.developmentbooks.service;

import com.digitalstork.developmentbooks.dto.BasketDto;
import com.digitalstork.developmentbooks.dto.BasketPriceDto;
import org.springframework.stereotype.Service;

@Service
public class DevelopmentBooksService implements IDevelopmentBooksService {

    @Override
    public BasketPriceDto calculatePrice(BasketDto basket) {
        return null;
    }

}
