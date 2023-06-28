package com.ebidding.bwic.controller;


import com.ebidding.bwic.domain.Bwic;
import com.ebidding.bwic.service.BwicService;
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

    @GetMapping()
    public ResponseEntity<Bwic> getBwic(@RequestParam("cusip") String cusip) {
        this.bwicService.findByCusip(cusip);
        int a=1+1;
        return ResponseEntity.ok(this.bwicService.findByCusip(cusip));
    }

}
