package com.ebidding.bid.repository;

import com.ebidding.bid.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    //Optional<Bid> findByPrice(Double price);
    Optional<Bid> findById(Long bidId);
}
