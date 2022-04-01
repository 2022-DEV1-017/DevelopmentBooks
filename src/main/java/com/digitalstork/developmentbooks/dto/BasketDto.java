package com.digitalstork.developmentbooks.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketDto {

    @NotEmpty(message = "bookQuantities must be provided !")
    Map<String, Integer> bookQuantities;
}
