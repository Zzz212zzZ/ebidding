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

    //实现了通过name查找数据库中的数据的接口
    @GetMapping()
    public ResponseEntity<Account> getAccount(@RequestParam("name") String name) {
//      Account account= accountService.findByName(name);
        return ResponseEntity.ok(this.accountService.findByName(name));
    }
}
