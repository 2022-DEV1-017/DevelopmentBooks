package com.digitalstork.developmentbooks.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketDto {

    Map<String, Integer> bookQuantities;
}
