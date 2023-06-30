package com.ebidding.bid.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BidRankDTO {
    @Id

    private int bidId;

    private int accountId;

    private int bwicId;

    private Double price;

    private LocalDate time;


}
