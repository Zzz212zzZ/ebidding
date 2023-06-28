package com.ebidding.bid.controller;

import com.ebidding.account.api.AccountDTO;
import com.ebidding.bid.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/bids")
public class BidController {
    @Autowired
    private BidService bidService;

    @GetMapping()
    public ResponseEntity<AccountDTO> getBid(@RequestParam("name") String name) {
        return ResponseEntity.ok(this.bidService.findByName(name));
    }
}
