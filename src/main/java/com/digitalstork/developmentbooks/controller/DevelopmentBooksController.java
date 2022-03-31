package com.digitalstork.developmentbooks.controller;

import com.digitalstork.developmentbooks.dto.BasketDto;
import com.digitalstork.developmentbooks.dto.BasketPriceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/development-books")
public class DevelopmentBooksController {

    @PostMapping("/calculate-price")
    ResponseEntity<BasketPriceDto> calculatePrice(@RequestBody BasketDto basket) {
        return null;
    }

}
