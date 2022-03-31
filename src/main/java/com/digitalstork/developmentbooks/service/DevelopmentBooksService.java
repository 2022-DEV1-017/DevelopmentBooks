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

        if (basket.getBookQuantities().size() == 1) {
            DiscountDto discount = new DiscountDto();
            discount.setRate(0F);
            discount.setUnitPrice(50.0);

            BasketPriceDto basketPrice = new BasketPriceDto();
            basketPrice.setDiscounts(Collections.singleton(discount));

            return basketPrice;
        } else {
            return null;
        }
    }

}
