package com.ebidding.bid.repository;

import com.ebidding.bid.domain.BidRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BidRankRepository extends JpaRepository<BidRank,Long> {
    Optional<BidRank> findBybidId(Long bidId);
}
