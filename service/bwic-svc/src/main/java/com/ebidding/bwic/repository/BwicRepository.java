package com.ebidding.bwic.repository;

import com.ebidding.bwic.domain.Bwic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BwicRepository extends JpaRepository<Bwic, Long> {
    //SELECT * FROM BOND WHERE CUSIP = ?
    Optional<Bwic> findByCusip(String cusip);

}
