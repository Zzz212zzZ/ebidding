package com.ebidding.bid.api;
import java.util.List;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bid-service", path = "api/v1/bid-service", url = "${ebidding.bid-endpoint}")
public interface BidClient {
//    @GetMapping("/{bwicId}/bid/success")
//    Bid getSuccesBidByBwicid(@PathVariable("bwicId") Long bwicId);
    //不能引入bid-svc的Bid类，因为bid-svc的Bid类是domain层的，而bid-api的Bid类是api层的，两者不同。并且引入会造成循环依赖。


    @GetMapping("/bwics/{bwicId}/ongoing-part-items")
    List<BidRankItemDataDTO> getBidRankingsByBwicId(@PathVariable("bwicId") Long bwicId);

}

