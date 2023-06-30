package com.ebidding.bid.controller;

import com.ebidding.account.api.AccountDTO;
import com.ebidding.bid.api.BidCreateRequestDTO;
import com.ebidding.bid.domain.Bid;
import com.ebidding.bid.domain.BidRank;
import com.ebidding.bid.service.BidService;
import com.ebidding.common.auth.AuthConstant;
import com.ebidding.common.auth.Authorize;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/bids")
public class BidController {
    @Autowired
    private BidService bidService;

    @Autowired
    private ModelMapper modelMapper;
//
//    @RequestHeader(AuthConstant.X_JWT_ID_HEADER) String userId;
//
    @GetMapping()//默认"api/v1/bids"
    @Authorize(AuthConstant.TRADER)
    public ResponseEntity<AccountDTO> getBid(@RequestParam("name") String name) {
        return ResponseEntity.ok(this.bidService.findByName(name));
    }

    @GetMapping("/bidIds")//"api/v1/bids/bidids"
    public ResponseEntity<Bid> getBid(@RequestParam("bid_id") Long bidId){
        return ResponseEntity.ok(this.bidService.findById(bidId));
    }

//    @PostMapping("/creates")
//    public ResponseEntity<BidCreateRequestDTO> createBid(@RequestBody BidCreateRequestDTO bidCreateRequestDTO){
//        Bid bidRequest = modelMapper.map(bidCreateRequestDTO,Bid.class);
//    }

    @GetMapping("/bidRanks")
    public  ResponseEntity<BidRank> getBidRank(@RequestParam("bid_id") Long bidId){
        return ResponseEntity.ok(this.bidService.findBybidId(bidId));
    }
}
