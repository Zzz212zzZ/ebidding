package com.ebidding.bid.service;

import com.ebidding.account.api.AccountDTO;
import com.ebidding.account.api.AccountClient;
import com.ebidding.bwic.api.BwicClient;
import com.ebidding.bid.api.PriceResponseDTO;
import com.ebidding.bid.domain.Bid;
import com.ebidding.bid.domain.BidRank;
import com.ebidding.bid.domain.BidRankPK;
import com.ebidding.bid.repository.BidRankRepository;
import com.ebidding.bid.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
//针对标有 @NonNull 注解的变量和 final 变量进行参数的构造方法。
public class BidService {

    @Autowired
    private final AccountClient accountClient;

    @Autowired
    private BwicClient bwicClient;


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


        // 1. 添加AccountId和BwicId,再添加时间戳
        BidRank bidRank = new BidRank();
        BidRankPK embbedeId = new BidRankPK(bid.getAccountId(), bid.getBwicId());
        bidRank.setId(embbedeId);
        bidRank.setPrice(bid.getPrice());
        Timestamp preTime=new Timestamp(System.currentTimeMillis());
        bidRank.setTime(preTime);
        bid.setTime(preTime);

        //2.现在获取排名
        this.bidRankRepository.save(bidRank);
        Long ranking = this.getRankByBwicIdAndAccountId(bid.getBwicId(),bid.getAccountId());

        // 3. 更新Bid的排名
        bid.setRanking(ranking);
        this.bidRepository.save(bid);

        //最后还要更新bwic中的bidCounts，last_bid_time，和present_price(如果比present_price高的话)
        //这个方法还要更新bond中的transaction_counts
        bwicClient.updateBwic(bid.getBwicId(),bid.getPrice(),bid.getTime());
        return bid;
    }

    public Long getRankByBwicIdAndAccountId(Long bwicId, Long accountId) {
        // 检查bidRank是否存在
        Long ranking = bidRankRepository.getRanking(bwicId,accountId);
       return ranking;

    }

    public Long getParticipantCount(Long bwicId) {
        return bidRankRepository.countByBwicId(bwicId);
    }

    public PriceResponseDTO getPrice(Long bwicId, Long accountId) {

        PriceResponseDTO response = new PriceResponseDTO();
        BidRank bidRank = this.bidRankRepository.findByBwicIdAndAccountId(bwicId, accountId).orElseThrow(()-> new NoSuchElementException("Record not found"));
        response.setPrice(bidRank.getPrice());
        Long rank = this.getRankByBwicIdAndAccountId(bwicId, accountId);
        if (rank == 1) {
            response.setIsFirst(true);
            if (this.getParticipantCount(bwicId) > 1) {
                Double secondPrice = this.bidRankRepository.getSecondHighestPrice(bwicId, accountId).orElse(null);
                response.setSecondPrice(secondPrice);
            }
        } else {
            response.setIsFirst(false);
        }
        return response;
    }

//    public BidRank getByBidId(Long bidId){
//        return this.bidRankRepository.findByBidId(bidId).orElse(null);
//    }


}