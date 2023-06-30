package com.ebidding.bid.service;

import com.ebidding.account.api.AccountDTO;
import com.ebidding.account.api.AccountClient;
import com.ebidding.bid.domain.Bid;
import com.ebidding.bid.domain.BidRank;
import com.ebidding.bid.repository.BidRankRepository;
import com.ebidding.bid.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

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

    @Autowired
    private ModelMapper modelMapper;

    public AccountDTO getByName(String inputName) {//调用account-api
        return this.accountClient.getAccount(inputName).getBody();
    }

    public Bid getByBidId(Long bidId) {
        return this.bidRepository.findByBidId(bidId).orElse(null);
    }


    public Bid createBid(Bid bid) {
        // 1. 添加时间戳，并将 Bid 存入数据库
        bid.setTime(new Timestamp(System.currentTimeMillis()));
        Bid savedBid = bidRepository.save(bid);
        Long bidId = savedBid.getBidId();  // 获取保存后的 bidId

        // 2. 设置 bidId，将 Bid 转为 BidRank，并存入数据库
        bid.setBidId(bidId);
        BidRank bidRank = modelMapper.map(bid, BidRank.class);
        BidRank savedBidRank = bidRankRepository.save(bidRank);

        // 3. 通过 bidId 和 ranking 查询 bidRank 表中的 rank 字段
        Long ranking = bidRankRepository.getRanking(bid.getBwicId(), bid.getPrice(), bid.getTime());

        // 4. 通过 bidId 和 ranking 更新 bidRank 表中的 rank 字段
        bid.setRanking(ranking);
        bidRepository.save(bid);

        return savedBid;
    }



//    public BidRank getByBidId(Long bidId){
//        return this.bidRankRepository.findByBidId(bidId).orElse(null);
//    }


}