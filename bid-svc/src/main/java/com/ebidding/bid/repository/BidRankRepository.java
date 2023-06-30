package com.ebidding.bid.repository;

import com.ebidding.bid.domain.Bid;
import com.ebidding.bid.domain.BidRank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BidRankRepository extends JpaRepository<BidRank,Long> {
    Optional<BidRank> findBybidId(Long bidId);
    Optional<Bid> updateBid(Long bidId, Bid bid);
}
