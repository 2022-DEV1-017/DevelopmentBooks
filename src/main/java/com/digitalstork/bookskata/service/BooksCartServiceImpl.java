package com.digitalstork.bookskata.service;

import com.digitalstork.bookskata.dto.BookDto;
import com.digitalstork.bookskata.dto.BooksCartDto;
import com.digitalstork.bookskata.dto.BooksCartPriceDto;
import com.digitalstork.bookskata.dto.DiscountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.digitalstork.bookskata.constants.BooksPricingConstants.*;

@Service
@Slf4j
public class BooksCartServiceImpl implements BooksCartService {

    private final BooksService booksService;

    public BooksCartServiceImpl(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public BooksCartPriceDto calculatePrice(BooksCartDto cart) {

        if (log.isInfoEnabled()) {
            log.info("Calculating price ...");
        }

        Collection<DiscountDto> discounts = new ArrayList<>();
        double totalPrice = 0.0;

        // calculate a discount while there are still remaining book copies in cart
        while (true) {

            if (log.isInfoEnabled()) {
                log.info("Hart has now {}", cart.getBookQuantities());
            }

            // calculate maximum number of copies of the same different books
            Optional<Integer> optionalMin = cart.getBookQuantities().values().stream()
                    .filter(i -> i > 0)
                    .min(Comparator.naturalOrder());

            if (optionalMin.isEmpty()) {
                break;
            }

            // generate a discount summary for each group
            DiscountDto discount = processDiscount(optionalMin.get(), cart.getBookQuantities());

            discounts.add(discount);
            totalPrice += discount.getUnitPrice() * discount.getBooks().size() * discount.getCopies();
        }

        if (log.isInfoEnabled()) {
            log.info("Total Price is : {}", totalPrice);
        }

        return BooksCartPriceDto.builder()
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

        if (log.isInfoEnabled()) {
            log.info("Taking {} copy of {}", copies, books);
        }

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
