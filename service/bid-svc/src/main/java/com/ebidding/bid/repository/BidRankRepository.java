package com.ebidding.bid.repository;

import com.ebidding.bid.domain.Bid;
import com.ebidding.bid.domain.BidRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface BidRankRepository extends JpaRepository<BidRank, Long> {
    @Query(nativeQuery = true, value = "SELECT COUNT(*) + 1 FROM bidrank WHERE bwic_id = :bwicId AND (price > :price OR (price = :price AND time < :time)) AND not (bwic_id = :bwicId AND account_id = :accountId)")
    Long getRanking(@Param("bwicId") Long bwicId, @Param("accountId") Long accountId, @Param("price") Double price, @Param("time") Timestamp time);

}


