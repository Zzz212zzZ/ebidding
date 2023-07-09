package com.ebidding.bwic.service;

import com.ebidding.bid.api.BidClient;
import com.ebidding.bid.api.BidRankItemDataDTO;
import com.ebidding.bwic.api.BwicDTO;
import com.ebidding.bwic.api.BwicUpcomingFullRecord;
import com.ebidding.bwic.api.BwicOngoingRecordResponseDTO;
import com.ebidding.bwic.api.BwicUpcomingRecordResponseDTO;
import com.ebidding.bwic.domain.Bwic;
import com.ebidding.bwic.repository.BwicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BwicService {
    @Autowired
    private BwicRepository bwicRepository;

    @Autowired
    private BondService bondService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BidClient bidClient;



    public Optional<BwicDTO> saveBwic(String bondId, double startPrice, Timestamp startTime, Timestamp dueTime, double size) {
        Bwic bwic = Bwic.builder()
                .bondId(bondId)
                .startPrice(startPrice)
                .presentPrice(startPrice)
                .startTime(startTime)
                .dueTime(dueTime)
                .lastBidTime(startTime)
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

    public double getBwicPrice(Long bwicId) {
        Bwic bwic = this.bwicRepository.findByBwicId(bwicId).orElse(null);
        return bwic.getPresentPrice();
    }

    public boolean isActive(Long bwicId) {
        Bwic bwic = this.bwicRepository.findByBwicId(bwicId).orElse(null);
        if (bwic == null) {
            throw new RuntimeException("Bwic not found");
        }
        LocalDateTime dueTime = bwic.getDueTime().toLocalDateTime();


        // 查询竞拍成功的用户并发送通知
        // TODO 这里通过feign调用 bid-svc服务的api/v1/bids/getSuccesBidByBwicid接口即可




        return dueTime.isAfter(LocalDateTime.now());
    }


    public Map<String, List<Bwic>> getHistoryRecords() {
        Timestamp timestampNow = Timestamp.valueOf(LocalDateTime.now());
        List<Bwic> activeBwics = bwicRepository.findAllByDueTimeAfterOrderByDueTimeAsc(timestampNow);
        List<Bwic> inactiveBwics = bwicRepository.findAllByDueTimeBeforeOrderByDueTimeDesc(timestampNow);

        Map<String, List<Bwic>> result = new HashMap<>();
        result.put("active", activeBwics);
        result.put("inactive", inactiveBwics);

        return result;
    }

    //---------------------------------------------查找正在进行的bwic------------------------------------------------
    public List<BwicOngoingRecordResponseDTO> getOngoingBwics() {

        List<Bwic> ongoingBwics = bwicRepository.findOngoingBwics();
        List<BwicOngoingRecordResponseDTO> responseDTOs = new ArrayList<>();

        for (Bwic bwic : ongoingBwics) {
            BwicOngoingRecordResponseDTO dto = modelMapper.map(bwic, BwicOngoingRecordResponseDTO.class);

            //因为前端展示的table是标的maxPrice，而数据库中的是presentPrice，但含义是一样的，所以这里将presentPrice赋值给maxPrice
            dto.setMaxPrice(bwic.getPresentPrice());

            dto.setCusip(getBondCusip(bwic.getBondId()));
            dto.setIssuer(getBondIssuer(bwic.getBondId()));



            List<BidRankItemDataDTO> bidRankings = bidClient.getBidRankingsByBwicId(bwic.getBwicId());

            // set the children property of dto with the obtained bidRankings
            dto.setChildren(bidRankings);

            responseDTOs.add(dto);
        }


        return responseDTOs;

    }

    //---------------------------------------------查找正在进行的bwic------------------------------------------------


    //---------------------------------------------查找还未开始的bwic------------------------------------------------
    public List<BwicUpcomingRecordResponseDTO> getUpcomingBwics() {

            List<Bwic> incomingBwics = bwicRepository.findUpcomingBwics();
            List<BwicUpcomingRecordResponseDTO> responseDTOs = new ArrayList<>();

            for (Bwic bwic : incomingBwics) {
                BwicUpcomingRecordResponseDTO dto = modelMapper.map(bwic, BwicUpcomingRecordResponseDTO.class);

                dto.setCusip(getBondCusip(bwic.getBondId()));
                dto.setIssuer(getBondIssuer(bwic.getBondId()));
                responseDTOs.add(dto);
            }
            return responseDTOs;
    }

    //---------------------------------------------查找还未开始的bwic------------------------------------------------



    //---------------------------------------------查找已经结束的bwic------------------------------------------------
    public List<BwicOngoingRecordResponseDTO> getEndedBwics() {

                List<Bwic> endedBwics = bwicRepository.findEndedBwics();
                List<BwicOngoingRecordResponseDTO> responseDTOs = new ArrayList<>();

                for (Bwic bwic : endedBwics) {
                    BwicOngoingRecordResponseDTO dto = modelMapper.map(bwic, BwicOngoingRecordResponseDTO.class);

                    dto.setCusip(getBondCusip(bwic.getBondId()));
                    dto.setIssuer(getBondIssuer(bwic.getBondId()));
                    responseDTOs.add(dto);
                }
                return responseDTOs;
    }






    //---------------------------------------------查找bond的属性接口------------------------------------------------
    public String getBondCusip(String bondId) {
        String cusip = bondService.findCuSipByBondId(bondId);
        return cusip;
    }
    //---------------------------------------------查找bond的属性接口------------------------------------------------
    public String getBondIssuer(String bondId) {
        String issuer = bondService.findIssuerByBondId(bondId);
        return issuer;
    }




    public String getCusipByBwicId(Long bwicId) {
        Bwic bwic = this.bwicRepository.findByBwicId(bwicId).orElse(null);
        if (bwic == null) {
            throw new RuntimeException("Bwic not found");
        }
        String bondId = bwic.getBondId();
        return getBondCusip(bondId);
    }

    public String getIssuerByBwicId(Long bwicId) {
        Bwic bwic = this.bwicRepository.findByBwicId(bwicId).orElse(null);
        if (bwic == null) {
            throw new RuntimeException("Bwic not found");
        }
        String bondId = bwic.getBondId();
        return getBondIssuer(bondId);
    }



    //加入Transactional，防止并发
    @Transactional
    public void incrementBidCount(Long bwicId) {
        bwicRepository.incrementBidCount(bwicId);
    }



    public void updateBwicAndBond(Long bwicId, double price, Timestamp time) {
        Bwic bwic = this.bwicRepository.findById(bwicId)
                .orElseThrow(() -> new RuntimeException("Bwic not found"));

        bwic.setBidCounts(bwic.getBidCounts() + 1);
        bwic.setLastBidTime(time);

        if (price  > bwic.getPresentPrice()) {
            bwic.setPresentPrice(price);
        }

        this.bwicRepository.save(bwic);

        //更新bond表
        bondService.incrementTransactionCount(bwic.getBondId());

    }

    public Bwic findByBwicId(Long bwicId){
        return this.bwicRepository.findByBwicId(bwicId).orElse(null);
    }

////    public List<Bwic> findByCusip(String cusip) {
////        return  this.bwicRepository.findByCusip(cusip).orElse(null);
//    }

    public Bwic findByBondId(String bondId){
        return this.bwicRepository.findByBondId(bondId).orElse(null);
    }


    public void updateBwicFullRecord(Long bwicId, BwicUpcomingFullRecord record) {
        Bwic bwic = bwicRepository.findById(bwicId)
                .orElseThrow(() -> new RuntimeException("Bwic not found"));

        bwic.setBondId(record.getBondId());
        bwic.setSize(record.getSize());
        bwic.setStartPrice(record.getStartPrice());
        bwic.setStartTime(record.getStartTime());
        bwic.setDueTime(record.getDueTime());

        bwicRepository.save(bwic);
    }

}