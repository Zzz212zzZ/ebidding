package com.ebidding.bid.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BidCreateResponseDTO {
    private Long accountId;

    private Long bwicId;

    private Double price;

    private LocalDateTime time;

}