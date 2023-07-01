package com.ebidding.bwic.api;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@FeignClient(name = "bwic-service", path = "api/v1/bwics", url= "${ebidding.bwic-endpoint}" )
public interface BwicClient {
    @GetMapping("/cusip")
    ResponseEntity<String> getCusip(@RequestParam("bwicId") Long bwicId);


    @PostMapping("/update")
    ResponseEntity<Void> updateBwic(@RequestParam("bwicId") Long bwicId,
                                    @RequestParam("price") double price,
                                    @RequestParam("time") Timestamp time);
}