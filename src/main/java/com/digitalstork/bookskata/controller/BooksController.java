package com.digitalstork.bookskata.controller;

import com.digitalstork.bookskata.dto.BookDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Tag(name = "Books Controller", description = "Use this APIs to get development books discounts")
@RestController()
@RequestMapping("/api/books")
public class BooksController {

    @Operation(summary = "API to get all available books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all available books", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class))}),
    })
    @GetMapping()
    ResponseEntity<Collection<BookDto>> getAllBooks() {
        return null;
    }

}
