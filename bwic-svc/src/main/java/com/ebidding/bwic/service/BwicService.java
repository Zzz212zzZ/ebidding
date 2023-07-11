package com.ebidding.bwic.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ebidding.bid.api.BidClient;
import com.ebidding.bwic.api.BwicDTO;
import com.ebidding.bwic.api.BwicRecordResponseDTO;
import com.ebidding.bid.api.BidClient;
import com.ebidding.bid.api.BidRankItemDataDTO;
import com.ebidding.bwic.api.*;
import com.ebidding.bwic.domain.Bwic;
import com.ebidding.bwic.domain.chat.ApiError;
import com.ebidding.bwic.domain.chat.ChatRequestDTO;
import com.ebidding.bwic.domain.chat.ChatResponseDTO;
import com.ebidding.bwic.repository.BwicRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
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



    @Autowired
    private ObjectMapper objectMapper;


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

        if(!dueTime.isAfter(LocalDateTime.now())){
            // 查询竞拍成功的用户并发送通知
            // TODO 这里通过feign调用 bid-svc服务的api/v1/bids/getSuccesBidByBwicid接口即可
        }

        return dueTime.isAfter(LocalDateTime.now());
    }


    public List<BwicRecordResponseDTO> getHistoryRecords() {
        List<BwicRecordResponseDTO> bwicRecordList = new ArrayList<>();
        Timestamp timestampNow = Timestamp.valueOf(LocalDateTime.now());

        List<Bwic> bwicList = bwicRepository.findAll();
        for (Bwic bwic : bwicList) {
            Boolean active = bwic.getDueTime().getTime() > timestampNow.getTime();
            BwicRecordResponseDTO dto = modelMapper.map(bwic, BwicRecordResponseDTO.class);
            dto.setCusip(getBondCusip(bwic.getBondId()));
            dto.setIssuer(getBondIssuer(bwic.getBondId()));
            dto.setActive(active);
            bwicRecordList.add(dto);
        }
        return bwicRecordList;
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
    public List<BwicEndedRecordResponseDTO> getEndedBwics() {

                List<Bwic> endedBwics = bwicRepository.findEndedBwics();
                List<BwicEndedRecordResponseDTO> responseDTOs = new ArrayList<>();

                for (Bwic bwic : endedBwics) {
                    BwicEndedRecordResponseDTO dto = modelMapper.map(bwic, BwicEndedRecordResponseDTO.class);

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

    public List<BwicRecordResponseDTO> getBwicByAccountId(Long accountId){
        List<BwicRecordResponseDTO> responseDTOs = new ArrayList<>();

        // 查询accountId的Bid
        List<Long> bwicIdList = bidClient.getBwicIdListByAccountId(accountId);
        Set<Long> bwicIdSet = new HashSet<>(bwicIdList);
        bwicIdSet.forEach(bwicId -> {
            Bwic bwic = bwicRepository.findByBwicId(bwicId).orElse(new Bwic());
            BwicRecordResponseDTO dto = modelMapper.map(bwic, BwicRecordResponseDTO.class);
            dto.setCusip(getBondCusip(bwic.getBondId()));
            dto.setIssuer(getBondIssuer(bwic.getBondId()));
            responseDTOs.add(dto);
        });
        return responseDTOs;
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

    public void deleteBwic(Long bwicId) {
        if(bwicRepository.existsById(bwicId)) {
            bwicRepository.deleteById(bwicId);
        } else {
            throw new RuntimeException("Bwic not found");
        }
    }
    public List<Bwic> getAllBwics(){
        return bwicRepository.findAll();
    }


    public String chatWithGPT(ChatRequestDTO request) {

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("localhost", 10808));
        requestFactory.setProxy(proxy);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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"; // replace with your actual OpenAI API key

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<ChatRequestDTO> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<ChatResponseDTO> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, ChatResponseDTO.class);

            ChatResponseDTO response = responseEntity.getBody();

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                return "No response";
            }

            return response.getChoices().get(0).getMessage().getContent();
        } catch (RestClientException e) {
            try {
                ApiError apiError = objectMapper.readValue(e.getMessage(), ApiError.class);
                return apiError.getError().getMessage();
            } catch (JsonProcessingException jsonException) {
                return "Error parsing API response: " + e.getMessage();
            }
        }
    }


    /**
     * 根據bwicId查詢用戶的排名
     * @param bwicId
     * @param accountId
     * @return
     */
    public Long getUserRankByBwicId(Long bwicId,Long accountId) {
        return bidClient.getUserRank(bwicId,accountId).getBody();
    }
}