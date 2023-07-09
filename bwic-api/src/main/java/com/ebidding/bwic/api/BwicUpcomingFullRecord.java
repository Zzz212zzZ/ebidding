package com.ebidding.bwic.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BwicUpcomingFullRecord {
    private Long bwicId;
    private String bondId;
    private Double size;
    private Double startPrice;
    private Timestamp startTime;
    private Timestamp dueTime;
}
