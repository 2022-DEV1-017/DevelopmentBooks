package com.digitalstork.bookskata.controller;

import com.digitalstork.bookskata.dto.BooksCartDto;
import com.digitalstork.bookskata.dto.BooksCartPriceDto;
import com.digitalstork.bookskata.service.BooksCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Books Cart Controller", description = "Use this APIs to get development books discounts")
@RestController()
@RequestMapping("/api/books-cart")
public class BooksCartController {

    private final BooksCartService booksCartService;

    public BooksCartController(BooksCartService booksCartService) {
        this.booksCartService = booksCartService;
    }

    @Operation(summary = "API for calculating the price of a set of books with all applied discounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns bill details", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BooksCartPriceDto.class))}),
            @ApiResponse(responseCode = "404", description = "Unavailable book", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    @PostMapping("/calculate-price")
    ResponseEntity<BooksCartPriceDto> calculatePrice(@RequestBody @Valid BooksCartDto cart) {
        return ResponseEntity.ok(booksCartService.calculatePrice(cart));
    }

}
