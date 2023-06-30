package com.ebidding.bid.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bid-service", path = "api/v1/bids", url = "${ebidding.bid-endpoint}")
public interface BidClient {
    @GetMapping("/")
    ResponseEntity<BidCreateRequestDTO> gitBid(@RequestParam("bid_id") Long bidId);
}

