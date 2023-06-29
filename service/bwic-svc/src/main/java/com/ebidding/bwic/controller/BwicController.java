package com.ebidding.bwic.controller;


import com.ebidding.bwic.domain.Bwic;
import com.ebidding.bwic.service.BwicService;
import com.ebidding.common.auth.AuthConstant;
import com.ebidding.common.auth.Authorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/byBwicId")
    @Authorize(AuthConstant.TRADER)
    public ResponseEntity<Bwic> getBwicByBwicId(@RequestParam("bwicId") String bwicId) {
        Bwic bwic = this.bwicService.findByBwicId(bwicId);
        return ResponseEntity.ok(bwic);
    }

}
