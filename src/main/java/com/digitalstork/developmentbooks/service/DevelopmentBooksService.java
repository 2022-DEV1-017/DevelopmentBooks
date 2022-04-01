package com.digitalstork.developmentbooks.service;

import com.digitalstork.developmentbooks.dto.BasketDto;
import com.digitalstork.developmentbooks.dto.BasketPriceDto;
import com.digitalstork.developmentbooks.dto.BookDto;
import com.digitalstork.developmentbooks.dto.DiscountDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

import static com.digitalstork.developmentbooks.constants.DevelopmentBooksConstants.*;

@Service
public class DevelopmentBooksService implements IDevelopmentBooksService {

    @Override
    public BasketPriceDto calculatePrice(BasketDto basket) {
        BasketPriceDto basketPrice = new BasketPriceDto();
        basketPrice.setDiscounts(new ArrayList<>());
        double totalPrice = 0.0;

        // calculate maximum number of copies of the same different books
        Optional<Integer> min = basket.getBookQuantities().values().stream()
                .filter(i -> i > 0)
                .min(Comparator.naturalOrder());

        while (min.isPresent()) {
            // generate a discount summary for each group
            DiscountDto discount = new DiscountDto();
            basketPrice.getDiscounts().add(discount);

            discount.setCopies(min.get());
            discount.setBooks(new ArrayList<>());

            // add book copies
            basket.getBookQuantities().entrySet().stream()
                    .filter(entry -> entry.getValue() > 0)
                    .forEach(entry -> {
                        entry.setValue(entry.getValue() - discount.getCopies());
                        discount.getBooks().add(new BookDto());
                    });

            discount.setRate(calculateDiscount(discount.getBooks().size()));
            discount.setUnitPrice(BOOK_PRICE * (1 - discount.getRate()));

            totalPrice += discount.getUnitPrice() * discount.getBooks().size() * discount.getCopies();

            min = basket.getBookQuantities().values().stream()
                    .filter(i -> i > 0)
                    .min(Comparator.naturalOrder());
        }
        basketPrice.setTotalPrice(totalPrice);

        return basketPrice;
    }

    private Double calculateDiscount(int numberOfDifferentBooks) {
        switch (numberOfDifferentBooks) {
            case 1: {
                return NO_DISCOUNT;
            }
            case 2: {
                return TWO_DIFFERENT_BOOKS_DISCOUNT;
            }
            case 3: {
                return THREE_DIFFERENT_BOOKS_DISCOUNT;
            }
            case 4: {
                return FOUR_DIFFERENT_BOOKS_DISCOUNT;
            }
            default:
            case 5: {
                return FIVE_DIFFERENT_BOOKS_DISCOUNT;
            }
        }
    }
}
