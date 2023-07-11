package com.ebidding.bwic.api;

import com.ebidding.bid.api.BidRankItemDataDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BwicUpcomingRecordResponseDTO {
    private Long bwicId;
    private String bondId;
    private String cusip;
    private String issuer;
    private double size;

    private double startPrice;

    //加入@JsonFormat注解，可以将Timestamp转换为指定格式的字符串
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp dueTime;

}