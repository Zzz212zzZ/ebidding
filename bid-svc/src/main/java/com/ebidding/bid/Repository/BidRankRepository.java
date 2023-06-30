package com.ebidding.bid.Repository;

import com.ebidding.bid.domain.BidRank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BidRankRepository extends JpaRepository<BidRank, String> {
    Optional<BidRank> findBybidId(int inputId);
}
