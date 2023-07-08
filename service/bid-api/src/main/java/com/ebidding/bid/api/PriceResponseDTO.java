package com.ebidding.bid.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponseDTO {

    private Double price;
    private Long Ranking;
    private Double secondPrice;


}