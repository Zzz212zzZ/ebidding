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

    private Long accountId;

    private Long bwicId;

    private Double price;

    private Long ranking;

    private LocalDate time;


}
