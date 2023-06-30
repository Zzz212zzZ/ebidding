package com.ebidding.bid.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Bid {
    @Id
    @Column(name = "bid_id", nullable = false)
    private int bidId;

    @Column(name = "account_id", nullable = false)
    private int accountId;

    @Column(name = "bwic_id", nullable = false)
    private int bwicId;

    @Column(name = "price", nullable = false)
    private Double price;

    private Long ranking;

    private LocalDate time;

}
