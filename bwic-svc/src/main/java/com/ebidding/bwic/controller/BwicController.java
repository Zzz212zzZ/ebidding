package com.ebidding.bwic.controller;


import com.ebidding.bwic.api.BwicDTO;
import com.ebidding.bwic.api.BwicRecordResponseDTO;
import com.ebidding.bwic.domain.Bwic;
import com.ebidding.bwic.service.BondService;
import com.ebidding.bwic.service.BwicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/bwic-service")
public class BwicController {
    @Autowired
    private BondService bondService;

    @Autowired
    private BwicService bwicService;

//    @GetMapping()
//    public ResponseEntity<Bwic> getBwic(@RequestParam("cusip") String cusip) {
//        this.bwicService.findByCusip(cusip);
//        return ResponseEntity.ok(this.bwicService.findByCusip(cusip));
//    }
    @PostMapping("/bwics")
    // [POST] http://localhost:8001/api/v1/bwics {}
    public ResponseEntity<BwicDTO> createBwic(@RequestBody BwicDTO bwicDTO) {
        Optional<BwicDTO> createdBwic = this.bwicService.saveBwic(
                bwicDTO.getBondId(),
                bwicDTO.getStartPrice(),
                bwicDTO.getStartTime(),
                bwicDTO.getDueTime(),
                bwicDTO.getSize()
        );

        return createdBwic.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @GetMapping("/bwics/{bwicId}/price")
    //返回现在的价格
    // [GET] http://localhost:8001/api/v1/bwics/price?bwicId={bwicId}
    public ResponseEntity<Double> getBwicPrice(@PathVariable("bwicId") Long bwicId) {
        double price = this.bwicService.getBwicPrice(bwicId);
        return ResponseEntity.ok(price);
    }

    //根据bwicId获取cusip--------------->不需要，直接从统一的接口一起返回
    @GetMapping("/bwics/{bwicId}/cusip")
    // [GET] http://localhost:8001/api/v1/bwics/cusip?bwicId={bwicId}
    public ResponseEntity<String> getCusip(@PathVariable("bwicId") Long bwicId) {
        String cusip = this.bwicService.getCusipByBwicId(bwicId);
        return ResponseEntity.ok(cusip);
    }

    @GetMapping("/bwics/{bwicId}/issuer")
    // [GET] http://localhost:8001/api/v1/bwics/issuer?bwicId={bwicId}
    public ResponseEntity<String> getIssuer(@PathVariable("bwicId") Long bwicId) {
        String issuer = this.bwicService.getIssuerByBwicId(bwicId);
        return ResponseEntity.ok(issuer);
    }

    @GetMapping("/bwics/{bwicId}/status")
    public ResponseEntity<Boolean> isActive(@PathVariable("bwicId")  Long bwicId) {
        boolean isActive = bwicService.isActive(bwicId);
        return ResponseEntity.ok(isActive);
    }

    @GetMapping("/bwics/history")
    public ResponseEntity<Map<String, List<Bwic>>> getHistoryRecords() {
        Map<String, List<Bwic>> historyRecords = this.bwicService.getHistoryRecords();
        return ResponseEntity.ok(historyRecords);
    }

    @GetMapping("/bwics/ongoing")
    public ResponseEntity<List<BwicRecordResponseDTO>> getOngoingBwics() {
        List<BwicRecordResponseDTO> ongoingBwics = this.bwicService.getOngoingBwics();
        return ResponseEntity.ok(ongoingBwics);
    }

//    @GetMapping("/bwics/incoming")
//    public ResponseEntity<List<BwicRecordResponseDTO>> getIncomingBwics() {
//        List<BwicRecordResponseDTO> incomingBwics = this.bwicService.getIncomingBwics();
//        return ResponseEntity.ok(incomingBwics);
//    }
//
//    @GetMapping("/bwics/ended")
//    public ResponseEntity<List<BwicRecordResponseDTO>> getEndedBwics() {
//        List<BwicRecordResponseDTO> endedBwics = this.bwicService.getEndedBwics();
//        return ResponseEntity.ok(endedBwics);
//    }



    @PutMapping("/bwics/{bwicId}")
    public ResponseEntity<Void> updateBwic(@PathVariable("bwicId") Long bwicId,
                                           @RequestParam("price") double price,
                                           @RequestParam("time") Timestamp time) {
        this.bwicService.updateBwicAndBond(bwicId, price, time);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/bwics/{bwicId}/bybwicId")
    public ResponseEntity<Bwic> getBwicByBwicid(@PathVariable("bwicId") Long bwicId){
        return ResponseEntity.ok(this.bwicService.findByBwicId(bwicId));
    }
    @GetMapping("/bwics/{cusip}/bycusip")
    public ResponseEntity<Bwic> getBwicByCusip(@PathVariable("cusip") String cusip){
        return ResponseEntity.ok(this.bwicService.findByBondId(this.bondService.getBondid(cusip)));
    }
//
//    @GetMapping("/bwics/{bondId}")
//    public ResponseEntity<Bwic> getBwicByBondid(@PathVariable("bondId") String bondId){
//        return ResponseEntity.ok(this.bwicService.findByBondId(bondId));
//    }
//
//    @GetMapping("/bondIds")
//    public ResponseEntity<String> getBondId(@PathVariable("cusip") String cusip){
//        return ResponseEntity.ok(this.bondService.getBondidByCusip(cusip));
//    }




}
