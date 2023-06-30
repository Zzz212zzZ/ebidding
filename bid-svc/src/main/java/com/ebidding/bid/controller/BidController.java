package com.ebidding.bid.controller;

import com.ebidding.account.api.AccountDTO;
import com.ebidding.bid.api.dto.CreateBidDTO;
import com.ebidding.bid.domain.Bid;
import com.ebidding.bid.service.BidService;
import com.ebidding.common.auth.AuthConstant;
import com.ebidding.common.auth.Authorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/bids")
public class BidController {
    @Autowired
    private BidService bidService;

    @GetMapping()
    @Authorize(AuthConstant.TRADER)
    public ResponseEntity<AccountDTO> getBid(@RequestParam("name") String name) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String userId = request.getHeader("X-jwt-id");
        return ResponseEntity.ok(this.bidService.findByName(name));
    }

    @PostMapping("createBid")
    @Authorize(AuthConstant.TRADER)
    public ResponseEntity<String> createBid(@RequestBody CreateBidDTO bidCreateDTO) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String userId = request.getHeader("X-jwt-id");

        Bid bid = new Bid();
        bid.setAccountId(Integer.parseInt(userId));
        bid.setPrice(bidCreateDTO.getPrice());
        bid.setBwicId(bidCreateDTO.getBwicId());
        bidService.createBid(bid);
        return ResponseEntity.ok("Bid创建成功！");
    }


}
