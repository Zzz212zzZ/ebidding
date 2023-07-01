package com.ebidding.bid.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Bid {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "bid_id", nullable = false)
    private Long bidId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "bwic_id", nullable = false)
    private Long bwicId;

    @Column(name = "price", nullable = false)
    private Double price;

    private Long ranking;

    private LocalDate time;

}
