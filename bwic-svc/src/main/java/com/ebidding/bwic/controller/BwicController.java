package com.ebidding.bwic.controller;


import com.ebidding.bwic.api.BwicDTO;
import com.ebidding.bwic.domain.Bond;
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
import java.util.stream.Collectors;

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
    // [GET] http://localhost:8001/api/v1/bwics/price?bwicId={bwicId}
    public ResponseEntity<Double> getBwicPrice(@PathVariable("bwicId") Long bwicId) {
        double price = this.bwicService.getBwicPrice(bwicId);
        return ResponseEntity.ok(price);
    }

    //根据bwicId获取cusip
    @GetMapping("/bwics/{bwicId}/cusip")
    // [GET] http://localhost:8001/api/v1/bwics/cusip?bwicId={bwicId}
    public ResponseEntity<String> getCusip(@PathVariable("bwicId") Long bwicId) {
        String cusip = this.bwicService.getCusip(bwicId);
        return ResponseEntity.ok(cusip);
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

    @GetMapping("/bwics/{bwicId}/issuer")
    // [GET] http://localhost:8001/api/v1/bwics/issuer?bwicId={bwicId}
    public ResponseEntity<String> getIssuer(@PathVariable("bwicId") Long bwicId) {
        String issuer = this.bwicService.getIssuer(bwicId);
        return ResponseEntity.ok(issuer);
    }

    @GetMapping("/bwics/Allbonds")
    public List<Bond> getAllBonds() {
        return bondService.getAllBonds().stream().collect(Collectors.toList());
    }

    @GetMapping("/bwics/Allbwics")
    public List<Bwic> getAllBwics() {
        return bwicService.getAllBwics().stream().collect(Collectors.toList());
    }

}