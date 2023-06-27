package com.ebidding.service.controller;

//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("api/v1/accounts")
//public class AccountController {
//    @GetMapping()
//    public ResponseEntity<List<String>> getAccounts(){
//        return ResponseEntity.ok(new ArrayList<>());
//    }
//}
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
    public ResponseEntity<Account> getAccount(@RequestParam("name") String name) {
        return ResponseEntity.ok(this.accountService.findByName(name));
    }
}
