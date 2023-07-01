package com.ebidding.bid.Repository;

import com.ebidding.bid.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
    // Optional<Bid> findById(int inputId);

}
