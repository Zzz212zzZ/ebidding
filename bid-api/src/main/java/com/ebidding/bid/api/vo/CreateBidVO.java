package com.ebidding.bid.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class CreateBidVO {

    private int accountId;

    private int bwicId;

    private Double price;

    private Long ranking;

    private LocalDate time;


}
