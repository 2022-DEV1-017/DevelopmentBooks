package com.digitalstork.developmentbooks.service;

import com.digitalstork.developmentbooks.dto.BasketDto;
import com.digitalstork.developmentbooks.dto.BasketPriceDto;
import com.digitalstork.developmentbooks.dto.DiscountDto;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class DevelopmentBooksService implements IDevelopmentBooksService {

    @Override
    public BasketPriceDto calculatePrice(BasketDto basket) {

        DiscountDto discount = new DiscountDto();
        BasketPriceDto basketPrice = new BasketPriceDto();
        basketPrice.setDiscounts(Collections.singleton(discount));

        if (basket.getBookQuantities().size() == 1) {
            discount.setRate(0.0);
            discount.setUnitPrice(BOOK_PRICE);
        } else if (basket.getBookQuantities().size() == 2) {
            discount.setRate(0.05);
            discount.setUnitPrice(BOOK_PRICE * (1 - 0.05));
        }
        return basketPrice;
    }

}
