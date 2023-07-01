package com.ebidding.bwic.repository;

import com.ebidding.bwic.domain.Bond;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BondRepository extends JpaRepository<Bond, Long> {


    Optional<Bond> findByBondId(String bondId);
}
