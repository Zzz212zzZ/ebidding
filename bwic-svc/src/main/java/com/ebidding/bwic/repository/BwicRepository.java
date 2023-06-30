package com.ebidding.bwic.repository;

import com.ebidding.bwic.api.BwicDTO;
import com.ebidding.bwic.domain.Bwic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface BwicRepository extends JpaRepository<Bwic, Long> {
    Optional<Bwic> findByBwicId(long bwicId);

    List<Bwic> findAllByDueTimeAfterOrderByDueTimeAsc(LocalDateTime time);

    List<Bwic> findAllByDueTimeBeforeOrderByDueTimeDesc(LocalDateTime time);
}
