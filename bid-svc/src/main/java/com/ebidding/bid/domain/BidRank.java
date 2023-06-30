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
public class BidRank {

    @Id
    @Column(name = "bid_id", nullable = false)
    private int bidId;

    @Column(name = "account_id", nullable = false)
    private int accountId;

    private int bwicId;

    private Double price;

    private LocalDate time;
}
