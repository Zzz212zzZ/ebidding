package com.ebidding.bwic.service;

import com.ebidding.bwic.api.BwicDTO;
import com.ebidding.bwic.domain.Bwic;
import com.ebidding.bwic.repository.BondRepository;
import com.ebidding.bwic.repository.BwicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BwicService {
    @Autowired
    private BwicRepository bwicRepository;

    @Autowired
    private BondRepository bondRepository;

//    public Bwic findByCusip(String cusip) {
//        return this.bwicRepository.findByCusip(cusip).orElse(null);
//    }



    public Optional<BwicDTO> saveBwic(String bondId, BigDecimal startPrice, LocalDateTime startTime, LocalDateTime dueTime, double size) {
        Bwic bwic = Bwic.builder()
                .bondId(bondId)
                .startPrice(startPrice)
                .startTime(startTime)
                .dueTime(dueTime)
                .size(size)
                .bidCounts(0L)   // default value
                .build();

        Bwic savedBwic = bwicRepository.save(bwic);

        BwicDTO savedBwicDTO = BwicDTO.builder()
                .bondId(savedBwic.getBondId())
                .startPrice(savedBwic.getStartPrice())
                .startTime(savedBwic.getStartTime())
                .dueTime(savedBwic.getDueTime())
                .size(savedBwic.getSize())
                .build();

        return Optional.of(savedBwicDTO);
    }

    public BigDecimal getBwicPrice(Long bwicId) {
        Bwic bwic = this.bwicRepository.findByBwicId(bwicId).orElse(null);
        return bwic.getStartPrice();
    }

    public boolean isActive(Long bwicId) {
        Bwic bwic = this.bwicRepository.findByBwicId(bwicId).orElse(null);
        return bwic.getDueTime().isAfter(LocalDateTime.now());
    }

    public Map<String, List<Bwic>> getHistoryRecords() {
        List<Bwic> activeBwics = bwicRepository.findAllByDueTimeAfterOrderByDueTimeAsc(LocalDateTime.now());
        List<Bwic> inactiveBwics = bwicRepository.findAllByDueTimeBeforeOrderByDueTimeDesc(LocalDateTime.now());

        Map<String, List<Bwic>> result = new HashMap<>();
        result.put("active", activeBwics);
        result.put("inactive", inactiveBwics);

        return result;
    }


    public String getCusip(Long bwicId) {
        //先根据bwicId查询bwic表，获取bondId
        Bwic bwic = this.bwicRepository.findByBwicId(bwicId).orElse(null);
        String bondId = bwic.getBondId();
        //再根据bondId查询bond表，获取cusip
        String cusip = this.bondRepository.findByBondId(bondId).orElse(null).getCusip();
        return cusip;

    }
}
