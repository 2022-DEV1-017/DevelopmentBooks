package com.digitalstork.developmentbooks.controller;

import com.digitalstork.developmentbooks.dto.BasketDto;
import com.digitalstork.developmentbooks.dto.BasketPriceDto;
import com.digitalstork.developmentbooks.service.IDevelopmentBooksService;
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

@Tag(name = "Books Controller", description = "Use this APIs to get development books discounts")
@RestController()
@RequestMapping("/api/development-books")
public class DevelopmentBooksController {

    private final IDevelopmentBooksService developmentBooksService;

    public DevelopmentBooksController(IDevelopmentBooksService developmentBooksService) {
        this.developmentBooksService = developmentBooksService;
    }

    @Operation(summary = "API for calculating the price of a set of books with all applied discounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns bill details", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BasketPriceDto.class))}),
            @ApiResponse(responseCode = "404", description = "Unavailable book", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content)
    })
    @PostMapping("/calculate-price")
    ResponseEntity<BasketPriceDto> calculatePrice(@RequestBody @Valid BasketDto basket) {
        return ResponseEntity.ok(developmentBooksService.calculatePrice(basket));
    }

}
