package com.ebidding.bwic.controller;


import com.ebidding.bwic.api.BwicDTO;
import com.ebidding.bwic.domain.Bwic;
import com.ebidding.bwic.service.BwicService;
import com.ebidding.common.auth.AuthConstant;
import com.ebidding.common.auth.Authorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/bwics")
public class BwicController {
    @Autowired
    private BwicService bwicService;

    //    @GetMapping()
//    public ResponseEntity<Bwic> getBwic(@RequestParam("cusip") String cusip) {
//        this.bwicService.findByCusip(cusip);
//        return ResponseEntity.ok(this.bwicService.findByCusip(cusip));
//    }
    @PostMapping()
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

    @GetMapping("/price")
    // [GET] http://localhost:8001/api/v1/bwics/price?bwicId={bwicId}
    public ResponseEntity<BigDecimal> getBwicPrice(@RequestParam("bwicId") Long bwicId) {
        BigDecimal price = this.bwicService.getBwicPrice(bwicId);
        return ResponseEntity.ok(price);
    }

    @GetMapping("/active")
    public ResponseEntity<Boolean> isActive(@RequestParam("bwicId")  Long bwicId) {
        boolean isActive = bwicService.isActive(bwicId);
        return ResponseEntity.ok(isActive);
    }

    @GetMapping("/historyRecords")
    public ResponseEntity<Map<String, List<Bwic>>> getHistoryRecords() {
        Map<String, List<Bwic>> historyRecords = this.bwicService.getHistoryRecords();
        return ResponseEntity.ok(historyRecords);
    }

}
