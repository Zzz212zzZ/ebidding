package com.ebidding.bwic.repository;

import com.ebidding.bwic.api.BwicDTO;
import com.ebidding.bwic.domain.Bwic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface BwicRepository extends JpaRepository<Bwic, Long> {
    //SELECT * FROM BOND WHERE CUSIP = ?
//    Optional<Bwic> findByCusip(String cusip);

    //SELECT * FROM BOND WHERE BWIC_ID = ?
    Optional<Bwic> findByBwicId(long bwicId);

    List<Bwic> findAllByDueTimeAfterOrderByDueTimeAsc(LocalDateTime time);

    List<Bwic> findAllByDueTimeBeforeOrderByDueTimeDesc(LocalDateTime time);

    @Modifying  //告诉JPA这是一个update或者delete操作，可能会产生脏数据
    @Query("UPDATE Bwic b SET b.bidCounts = b.bidCounts + 1 WHERE b.bwicId = :bwicId")
    void incrementBidCount(@Param("bwicId") Long bwicId);



}