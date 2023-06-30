package com.ebidding.bid.service;

import com.ebidding.account.api.AccountDTO;
import com.ebidding.account.api.AccountClient;
import com.ebidding.bid.domain.Bid;
import com.ebidding.bid.domain.BidRank;
import com.ebidding.bid.repository.BidRankRepository;
import com.ebidding.bid.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//针对标有 @NonNull 注解的变量和 final 变量进行参数的构造方法。
public class BidService {

    private final AccountClient accountClient;

//    @Autowired
//    public BidService(AccountClient accountClient) {
//        this.accountClient = accountClient;
//    }用@RequiredArgsConstructor代替
    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private BidRankRepository bidRankRepository;

    public AccountDTO findByName(String inputName) {//调用account-api
        return this.accountClient.getAccount(inputName).getBody();
    }

    public Bid findById(Long bidId) {
        return this.bidRepository.findById(bidId).orElse(null);
    }

//    public Bid createBid(Bid bid){
//        return bidRepository.save(bid);
//    }
//
//    public BidRank update(Long bidId,BidRank bid){
//        return bid;
//    }

    public BidRank findBybidId(Long bidId){
        return this.bidRankRepository.findBybidId(bidId).orElse(null);
    }
}