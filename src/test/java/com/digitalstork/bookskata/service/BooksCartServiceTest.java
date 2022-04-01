package com.digitalstork.bookskata.service;

import com.digitalstork.bookskata.dto.BookDto;
import com.digitalstork.bookskata.dto.BooksCartDto;
import com.digitalstork.bookskata.dto.BooksCartPriceDto;
import com.digitalstork.bookskata.dto.DiscountDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static com.digitalstork.bookskata.constants.BooksPricingConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BooksCartServiceTest {

    @InjectMocks
    private BooksCartServiceImpl booksCartServiceImpl;

    @Mock
    private BooksServiceImpl booksServiceImpl;

    @Test
    void should_not_apply_discount_if_one_copy_only() {
        // Given
        BooksCartDto cart = BooksCartDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("1", 1);
                }})
                .build();

        // When
        when(booksServiceImpl.getBook(anyString())).thenReturn(new BookDto());
        BooksCartPriceDto cartPrice = booksCartServiceImpl.calculatePrice(cart);

        // Assertions
        assertNotNull(cartPrice);
        assertNotNull(cartPrice.getDiscounts());
        assertEquals(1, cartPrice.getDiscounts().size());
        DiscountDto discount = cartPrice.getDiscounts().stream().findAny().orElseThrow();
        assertEquals(NO_DISCOUNT, discount.getRate());
        assertEquals(BOOK_PRICE * (1 - discount.getRate()), discount.getUnitPrice());
    }

    @Test
    void should_apply_5_percent_discount_if_2_different_books() {
        // Given
        BooksCartDto cart = BooksCartDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("1", 1);
                    put("2", 1);
                }})
                .build();

        // When
        when(booksServiceImpl.getBook(anyString())).thenReturn(new BookDto());
        BooksCartPriceDto cartPrice = booksCartServiceImpl.calculatePrice(cart);

        // Assertions
        assertNotNull(cartPrice);
        assertNotNull(cartPrice.getDiscounts());
        assertEquals(1, cartPrice.getDiscounts().size());

        DiscountDto discount = cartPrice.getDiscounts().stream().findAny().orElseThrow();

        assertEquals(TWO_DIFFERENT_BOOKS_DISCOUNT, discount.getRate());
        assertEquals(BOOK_PRICE * (1 - discount.getRate()), discount.getUnitPrice());
    }

    @Test
    void should_apply_10_percent_discount_if_3_different_books() {
        // Given
        BooksCartDto cart = BooksCartDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("1", 1);
                    put("2", 1);
                    put("3", 1);
                }})
                .build();

        // When
        when(booksServiceImpl.getBook(anyString())).thenReturn(new BookDto());
        BooksCartPriceDto cartPrice = booksCartServiceImpl.calculatePrice(cart);

        // Assertions
        assertNotNull(cartPrice);
        assertNotNull(cartPrice.getDiscounts());
        assertEquals(1, cartPrice.getDiscounts().size());

        DiscountDto discount = cartPrice.getDiscounts().stream().findAny().orElseThrow();

        assertEquals(THREE_DIFFERENT_BOOKS_DISCOUNT, discount.getRate());
        assertEquals(BOOK_PRICE * (1 - discount.getRate()), discount.getUnitPrice());
    }

    @Test
    void should_apply_20_percent_discount_if_4_different_books() {
        // Given
        BooksCartDto cart = BooksCartDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("1", 1);
                    put("2", 1);
                    put("3", 1);
                    put("4", 1);
                }})
                .build();

        // When
        when(booksServiceImpl.getBook(anyString())).thenReturn(new BookDto());
        BooksCartPriceDto cartPrice = booksCartServiceImpl.calculatePrice(cart);

        // Assertions
        assertNotNull(cartPrice);
        assertNotNull(cartPrice.getDiscounts());
        assertEquals(1, cartPrice.getDiscounts().size());

        DiscountDto discount = cartPrice.getDiscounts().stream().findAny().orElseThrow();

        assertEquals(FOUR_DIFFERENT_BOOKS_DISCOUNT, discount.getRate());
        assertEquals(BOOK_PRICE * (1 - discount.getRate()), discount.getUnitPrice());
    }

    @Test
    void should_apply_25_percent_discount_if_5_different_books() {
        // Given
        BooksCartDto cart = BooksCartDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("1", 1);
                    put("2", 1);
                    put("3", 1);
                    put("4", 1);
                    put("5", 1);
                }})
                .build();

        // When
        when(booksServiceImpl.getBook(anyString())).thenReturn(new BookDto());
        BooksCartPriceDto cartPrice = booksCartServiceImpl.calculatePrice(cart);

        // Assertions
        assertNotNull(cartPrice);
        assertNotNull(cartPrice.getDiscounts());
        assertEquals(1, cartPrice.getDiscounts().size());

        DiscountDto discount = cartPrice.getDiscounts().stream().findAny().orElseThrow();

        assertEquals(FIVE_DIFFERENT_BOOKS_DISCOUNT, discount.getRate());
        assertEquals(BOOK_PRICE * (1 - discount.getRate()), discount.getUnitPrice());
    }

    @Test
    void should_apply_10_percent_discount_for_3_different_books_and_no_discount_for_duplicate_copy() {
        // Given
        BooksCartDto cart = BooksCartDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("1", 2);
                    put("2", 1);
                    put("3", 1);
                }})
                .build();

        // When
        when(booksServiceImpl.getBook(anyString())).thenReturn(new BookDto());
        BooksCartPriceDto cartPrice = booksCartServiceImpl.calculatePrice(cart);

        // Assertions
        assertNotNull(cartPrice);
        assertNotNull(cartPrice.getDiscounts());
        assertEquals(2, cartPrice.getDiscounts().size());

        DiscountDto discount10 = cartPrice.getDiscounts().stream()
                .filter(i -> THREE_DIFFERENT_BOOKS_DISCOUNT.equals(i.getRate()))
                .findAny()
                .orElse(null);

        assertNotNull(discount10);
        assertNotNull(discount10.getBooks());
        assertEquals(3, discount10.getBooks().size());
        assertEquals(1, discount10.getCopies());

        DiscountDto discount0 = cartPrice.getDiscounts().stream()
                .filter(i -> NO_DISCOUNT.equals(i.getRate()))
                .findAny()
                .orElse(null);

        assertNotNull(discount0);
        assertNotEquals(discount0, discount10);
        assertNotNull(discount0.getBooks());
        assertEquals(1, discount0.getBooks().size());
        assertEquals(1, discount0.getCopies());
    }

    @Test
    void should_process_total_price_of_cart() {
        // Given
        BooksCartDto cart = BooksCartDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("1", 2);
                    put("2", 2);
                    put("3", 2);
                    put("4", 1);
                    put("5", 1);
                }})
                .build();

        // When
        when(booksServiceImpl.getBook(anyString())).thenReturn(new BookDto());
        BooksCartPriceDto cartPrice = booksCartServiceImpl.calculatePrice(cart);

        // Assertions
        assertNotNull(cartPrice);
        assertNotNull(cartPrice.getDiscounts());
        assertEquals(2, cartPrice.getDiscounts().size());

        DiscountDto discount25 = cartPrice.getDiscounts().stream()
                .filter(i -> FIVE_DIFFERENT_BOOKS_DISCOUNT.equals(i.getRate()))
                .findAny()
                .orElse(null);

        assertNotNull(discount25);
        assertNotNull(discount25.getBooks());

        DiscountDto discount10 = cartPrice.getDiscounts().stream()
                .filter(i -> THREE_DIFFERENT_BOOKS_DISCOUNT.equals(i.getRate()))
                .findAny()
                .orElse(null);

        assertNotNull(discount10);
        assertNotEquals(discount10, discount25);
        assertNotNull(discount10.getBooks());

        assertEquals(50.0, BOOK_PRICE);
        assertEquals(0.10, THREE_DIFFERENT_BOOKS_DISCOUNT);
        assertEquals(0.25, FIVE_DIFFERENT_BOOKS_DISCOUNT);

        assertEquals(5, discount25.getBooks().size());
        assertEquals(1, discount25.getCopies());
        assertEquals(BOOK_PRICE * (1 - discount25.getRate()), discount25.getUnitPrice());

        assertEquals(3, discount10.getBooks().size());
        assertEquals(1, discount10.getCopies());
        assertEquals(BOOK_PRICE * (1 - discount10.getRate()), discount10.getUnitPrice());

        Double price =
                5 * discount25.getUnitPrice() * discount25.getCopies() +
                        3 * discount10.getUnitPrice() * discount10.getCopies();

        assertEquals(price, cartPrice.getTotalPrice());
    }

    @Test
    void should_return_cart_book_details() {

        // Given
        BooksCartDto cart = BooksCartDto.builder()
                .bookQuantities(new HashMap<>() {{
                    put("1", 1);
                }})
                .build();

        BookDto bookDto = BookDto.builder()
                .externalCode("1")
                .name("Clean Code")
                .author("Robert Martin")
                .year(2008)
                .build();

        // When
        when(booksServiceImpl.getBook("1")).thenReturn(bookDto);
        BooksCartPriceDto cartPrice = booksCartServiceImpl.calculatePrice(cart);

        // Assertions
        assertNotNull(cartPrice);
        assertNotNull(cartPrice.getDiscounts());
        assertEquals(1, cartPrice.getDiscounts().size());

        DiscountDto discount = cartPrice.getDiscounts().stream().findAny().orElseThrow();

        assertNotNull(discount);
        assertNotNull(discount.getBooks());
        assertEquals(1, discount.getBooks().size());

        BookDto discountBook = discount.getBooks().stream().findAny().orElseThrow();

        assertNotNull(discountBook);
        assertEquals(bookDto.getExternalCode(), discountBook.getExternalCode());
        assertEquals(bookDto.getName(), discountBook.getName());
        assertEquals(bookDto.getAuthor(), discountBook.getAuthor());
        assertEquals(bookDto.getYear(), discountBook.getYear());
    }

}
