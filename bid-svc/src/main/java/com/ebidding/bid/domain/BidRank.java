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
    private Long bidId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    private Long bwicId;

    private Double price;

    private LocalDate time;
}
