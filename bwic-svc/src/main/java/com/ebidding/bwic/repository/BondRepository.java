package com.ebidding.bwic.repository;

import com.ebidding.bwic.domain.Bond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BondRepository extends JpaRepository<Bond, Long> {


    Optional<Bond> findByBondId(String bondId);

    @Query(nativeQuery = true, value = "SELECT bond_id FROM bond WHERE cusip = :cusip")
    Optional<String> getBondid(@Param("cusip") String cusip);

    @Modifying
    @Query("UPDATE Bond b SET b.transactionCounts = b.transactionCounts + 1 WHERE b.bondId = :bondId")
    void incrementTransactionCount(@Param("bondId") String bondId);

}
