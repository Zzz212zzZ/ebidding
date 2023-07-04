package com.ebidding.bid.service;

import com.ebidding.account.api.AccountClient;
import com.ebidding.account.api.AccountDTO;
import com.ebidding.bid.Repository.BidRepository;
import com.ebidding.bid.api.vo.CreateBidVO;
import com.ebidding.bid.domain.Bid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class BidService {
    private final AccountClient accountClient;


    @Autowired
    private BidRepository bidRepository;

    public AccountDTO findByName(String inputName) {
        return this.accountClient.getAccount(inputName).getBody();
    }

    public void createBid(Bid bid){
        bid.setTime(LocalDate.now());
        bidRepository.save(bid);
    }
}
