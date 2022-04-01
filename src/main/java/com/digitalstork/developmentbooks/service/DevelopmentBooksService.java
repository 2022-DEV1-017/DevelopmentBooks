package com.digitalstork.developmentbooks.service;

import com.digitalstork.developmentbooks.dto.BasketDto;
import com.digitalstork.developmentbooks.dto.BasketPriceDto;
import com.digitalstork.developmentbooks.dto.BookDto;
import com.digitalstork.developmentbooks.dto.DiscountDto;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.digitalstork.developmentbooks.constants.DevelopmentBooksConstants.*;

@Service
public class DevelopmentBooksService implements IDevelopmentBooksService {

    private final IBooksService booksService;

    public DevelopmentBooksService(IBooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public BasketPriceDto calculatePrice(BasketDto basket) {
        Collection<DiscountDto> discounts = new ArrayList<>();
        double totalPrice = 0.0;

        // calculate a discount while there are still remaining book copies in basket
        while (true) {
            // calculate maximum number of copies of the same different books
            Optional<Integer> optionalMin = basket.getBookQuantities().values().stream()
                    .filter(i -> i > 0)
                    .min(Comparator.naturalOrder());

            if (optionalMin.isEmpty()) {
                break;
            }

            // generate a discount summary for each group
            DiscountDto discount = processDiscount(optionalMin.get(), basket.getBookQuantities());

            discounts.add(discount);
            totalPrice += discount.getUnitPrice() * discount.getBooks().size() * discount.getCopies();

        }

        return BasketPriceDto.builder()
                .discounts(discounts)
                .totalPrice(totalPrice)
                .build();
    }

    private DiscountDto processDiscount(Integer copies, Map<String, Integer> bookQuantities) {

        // take a number of copies for each available
        Collection<BookDto> books = new ArrayList<>();
        bookQuantities.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .forEach(entry -> {
                    books.add(booksService.getBook(entry.getKey()));
                    entry.setValue(entry.getValue() - copies);
                });

        Double rate = calculateDiscount(books.size());

        return DiscountDto.builder()
                .copies(copies)
                .books(books)
                .rate(rate)
                .unitPrice(BOOK_PRICE * (1 - rate))
                .build();
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
