package com.ebidding.bwic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "bwic")
public class Bwic {

    @Id
    @Column(name = "bwic_id", nullable = false)
    private Long bwicId;

    @Column(name = "bond_id", nullable = false)
    private String bondId;

    @Column(name = "size")
    private double size;

    @Column(name = "start_price")
    private BigDecimal startPrice;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "due_time")
    private LocalDateTime dueTime;

    @Column(name = "bid_counts")
    private Long bidCounts;


}