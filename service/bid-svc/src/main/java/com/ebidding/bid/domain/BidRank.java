package com.ebidding.bid.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "bidrank")
public class BidRank {
    @Id
    @Column(name = "bid_id",nullable = false)
    private Long bidId;

    private Long accountId;

    private Double price;

    private Timestamp time;

    private Long bwicId;
}

