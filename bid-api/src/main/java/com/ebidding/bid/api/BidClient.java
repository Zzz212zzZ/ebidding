package com.ebidding.bid.api;

import com.ebidding.bid.domain.Bid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bid-service", path = "api/v1/bid-service", url = "${ebidding.bid-endpoint}")
public interface BidClient {
    @GetMapping("/{bwicId}/bid/success")
    Bid getSuccesBidByBwicid(@PathVariable("bwicId") Long bwicId);


}

