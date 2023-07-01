package com.ebidding.bid.controller;

import com.ebidding.account.api.AccountDTO;
import com.ebidding.bid.api.BidCreateRequestDTO;
import com.ebidding.bid.domain.Bid;
import com.ebidding.bid.domain.BidRank;
import com.ebidding.bid.domain.BidRankPK;
import com.ebidding.bid.service.BidService;
import com.ebidding.common.auth.AuthConstant;
import com.ebidding.common.auth.Authorize;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        return ResponseEntity.ok(this.bidService.getByName(name));
    }

    @Authorize(AuthConstant.TRADER)
    @GetMapping("/bidIds")//"api/v1/bids/bidids"
    public ResponseEntity<Bid> getBid(@RequestParam("bid_id") Long bidId){
        return ResponseEntity.ok(this.bidService.getByBidId(bidId));
    }

    @PostMapping("/creates")
    public ResponseEntity<BidCreateRequestDTO> createBid(@RequestBody BidCreateRequestDTO bidCreateRequestDTO, HttpServletRequest request){
        //获取header里面的bid_id
        String currentAccountId = request.getHeader(AuthConstant.X_JWT_ID_HEADER);
        Long accountId =Long.valueOf(currentAccountId);

//        // 创建BidRankPK对象，包含accountId和bwicId
//        BidRankPK id = new BidRankPK(accountId.get(), bidCreateRequestDTO.getBwicId());

        //创建新的Bid
        Bid bid = new Bid();
        //设置id
        bid.setAccountId(accountId);
        bid.setPrice(bidCreateRequestDTO.getPrice());
        bid.setBwicId(bidCreateRequestDTO.getBwicId());

        // 现在的bid只有输入的price和bwicId（在BidRankPK中），以及从请求头获取的accountId。但是还缺少bidTime和bidRank
        bidService.createBid(bid);
        return ResponseEntity.status(HttpStatus.CREATED).body(bidCreateRequestDTO);
    }



//    @GetMapping("/bidRanks")
//    public  ResponseEntity<BidRank> getBidRank(@RequestParam("bid_id") Long bidId){
//        return ResponseEntity.ok(this.bidService.getBybidId(bidId));
//    }
}
