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
public class BidDTO {
    @Id

    private int bidId;

    private int bwicId;

    private Double price;

    private Long ranking;

    private LocalDate time;


}
