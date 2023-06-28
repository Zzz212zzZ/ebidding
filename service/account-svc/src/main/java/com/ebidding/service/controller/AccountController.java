package com.ebidding.service.controller;

import com.ebidding.service.domain.Account;
import com.ebidding.service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;
import com.ebidding.account.api.AccountDTO;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    // PathVariable v.s. RequestParam v.s. RequestBody
    // http://localhost:8080/api/v1/accounts?name={inputName}
    // http://localhost:8080/api/v1/accounts/1
    // [POST] http://localhost:8080/api/v1/accounts {}

    public ResponseEntity<AccountDTO> getAccount(@RequestParam("name") String name) {
        Account account = this.accountService.findByName(name);
        // Account -> AccountDTO
        AccountDTO accountDTO = this.modelMapper.map(account, AccountDTO.class);
        return ResponseEntity.ok(accountDTO);
    }
}
