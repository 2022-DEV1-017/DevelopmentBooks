package com.digitalstork.developmentbooks.service;

import com.digitalstork.developmentbooks.dto.BasketDto;
import com.digitalstork.developmentbooks.dto.BasketPriceDto;
import com.digitalstork.developmentbooks.dto.DiscountDto;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.digitalstork.developmentbooks.constants.DevelopmentBooksConstants.*;

@Service
public class DevelopmentBooksService implements IDevelopmentBooksService {

    @Override
    public BasketPriceDto calculatePrice(BasketDto basket) {

        DiscountDto discount = new DiscountDto();
        discount.setRate(calculateDiscount(basket.getBookQuantities().size()));
        discount.setUnitPrice(BOOK_PRICE * (1 - discount.getRate()));

        BasketPriceDto basketPrice = new BasketPriceDto();
        basketPrice.setDiscounts(Collections.singleton(discount));

        return basketPrice;
    }

    private Double calculateDiscount(int numberOfDifferentBooks) {
        switch (numberOfDifferentBooks) {
            case 1: {
                return NO_DISCOUNT;
            }
            default:
            case 2: {
                return TWO_DIFFERENT_BOOKS_DISCOUNT;
            }
        }
    }
}
