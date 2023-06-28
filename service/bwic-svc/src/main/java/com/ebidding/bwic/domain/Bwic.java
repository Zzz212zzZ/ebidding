package com.ebidding.bwic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Bwic {
    @Id
    @Column(name = "cusip",nullable = false)
    private String cusip;

    @Column(name = "size")
    private double size;

    @Column(name = "starting_price")
    private java.math.BigDecimal startingPrice;

    @Column(name = "due_date")
    private LocalDateTime dueDate;



}
