package com.ebidding.bid.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BidCreateResponseDTO {
    private Long accountId;

    private Long bwicId;

    private Double price;

    private Timestamp time;

}