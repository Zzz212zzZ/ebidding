package com.ebidding.bid.controller;

import com.ebidding.account.api.AccountDTO;
import com.ebidding.bid.api.BidCreateRequestDTO;
import com.ebidding.bid.api.BidCreateResponseDTO;
import com.ebidding.bid.api.PriceResponseDTO;
import com.ebidding.bid.domain.Bid;
import com.ebidding.bid.domain.BidRank;
import com.ebidding.bid.domain.BidRankPK;
import com.ebidding.bid.service.BidService;
import com.ebidding.bwic.api.BwicClient;
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

    @Autowired
    private BwicClient bwicClient;


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
    public ResponseEntity<BidCreateResponseDTO> createBid(@RequestBody BidCreateRequestDTO bidCreateRequestDTO, HttpServletRequest request){
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
        Bid createdBid = bidService.createBid(bid);

        //将Bid对象转换为BidCreateResponseDTO对象
        BidCreateResponseDTO responseDTO = new BidCreateResponseDTO();
        responseDTO.setPrice(createdBid.getPrice());
        responseDTO.setRanking(createdBid.getRanking());
        responseDTO.setTime(createdBid.getTime());
                    //下面将createdBid的bwicId转化为cusip。
        ResponseEntity<String> response = bwicClient.getCusip(createdBid.getBwicId());
        String cusip = response.getBody();
        responseDTO.setCusip(cusip);


        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    @GetMapping("/rank")
    public ResponseEntity<Long> getRealTimeRank(@RequestParam("bwicId") Long bwicId, @RequestHeader(AuthConstant.X_JWT_ID_HEADER) String accountId) {

        Long rank = this.bidService.getRankByBwicIdAndAccountId(bwicId, Long.valueOf(accountId));
        return ResponseEntity.ok(rank);

    }


    @GetMapping("/participants")
    public ResponseEntity<Long> getParticipantCount(@RequestParam("bwicId") Long bwicId) {
        Long participantCount = bidService.getParticipantCount(bwicId);
        return ResponseEntity.ok(participantCount);
    }


    @GetMapping("/price")
    public ResponseEntity<PriceResponseDTO> getPrice(@RequestParam("bwicId") Long bwicId, @RequestHeader(AuthConstant.X_JWT_ID_HEADER) String accountId) {
        PriceResponseDTO response = this.bidService.getPrice(bwicId, Long.valueOf(accountId));
        return ResponseEntity.ok(response);
    }






//    @GetMapping("/bidRanks")
//    public  ResponseEntity<BidRank> getBidRank(@RequestParam("bid_id") Long bidId){
//        return ResponseEntity.ok(this.bidService.getBybidId(bidId));
//    }
}