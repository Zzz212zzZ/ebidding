package com.ebidding.bid.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "bid")
public class Bid {
    @Id
    @Column(name = "bid_id", nullable = false)
    private Long bidId;

    private Long accountId;

    private Double price;

    private Long ranking;

    private LocalDateTime time;

    private Long bwicId;
}
