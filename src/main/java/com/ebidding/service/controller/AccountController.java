package com.ebidding.service.controller;

import com.ebidding.service.domain.Account;
import com.ebidding.service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping()
    // PathVariable v.s. RequestParam v.s. RequestBody
    // http://localhost:8080/api/v1/accounts?name={inputName}
    // http://localhost:8080/api/v1/accounts/1
    // [POST] http://localhost:8080/api/v1/accounts {}
    public ResponseEntity<Account> getAccount(@RequestParam("name") String name) {
        return ResponseEntity.ok(this.accountService.findByName(name));
    }
}