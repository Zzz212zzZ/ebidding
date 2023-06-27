package com.ebidding.service.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java. util.ArrayList;
import java .util.List;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {
    @GetMapping()
    public ResponseEntity<List<String>> getAccounts() {
        return ResponseEntity.ok(new ArrayList<>());
    }

}

