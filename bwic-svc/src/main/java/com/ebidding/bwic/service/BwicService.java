package com.ebidding.bwic.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ebidding.bwic.api.BwicDTO;
import com.ebidding.bwic.domain.Bond;
import com.ebidding.bwic.domain.Bwic;
import com.ebidding.bwic.repository.BondRepository;
import com.ebidding.bwic.repository.BwicRepository;
import com.ebidding.common.utils.WebSocketMessageUtil;
import com.ebidding.common.websocket.UserIdSessionManager;
import com.ebidding.common.websocket.enums.WebSocketMsgType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
    private BondService bondService;

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
        return bwic.getStartPrice();
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



    public String getCusip(Long bwicId) {
        //先根据bwicId查询bwic表，获取bondId
        Bwic bwic = this.bwicRepository.findByBwicId(bwicId).orElse(null);
        String bondId = bwic.getBondId();
        //再根据bondId查询bond表，获取cusip

        String cusip = bondService.findCuSipByBondId(bondId);
        return cusip;
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

    public String getIssuer(Long bwicId) {
        //先根据bwicId查询bwic表，获取bondId
        Bwic bwic = this.bwicRepository.findByBwicId(bwicId).orElse(null);
        String bondId = bwic.getBondId();
        //再根据bondId查询bond表，获取issuer

        String issuer = bondService.findIssuerByBondId(bondId);
        return issuer;
    }

    public List<Bwic> getAllBwics(){
        return bwicRepository.findAll();
    }

}