package com.ebidding.bwic.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BwicDTO {
    private String bondId;
    private Double startPrice;
    private LocalDateTime startTime;
    private LocalDateTime dueTime;
    private double size;
}
