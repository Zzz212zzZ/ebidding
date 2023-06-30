package com.ebidding.account.controller;

import com.ebidding.account.api.AccountDTO;
import com.ebidding.account.api.LoginRequestDTO;
import com.ebidding.account.api.LoginResponseDTO;
import com.ebidding.account.domain.Account;
import com.ebidding.account.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    //实现了通过name查找数据库中的数据的接口
    @GetMapping()
    public ResponseEntity<AccountDTO> getAccount(@RequestParam("name") String name) {
//      Account account= accountService.findByName(name);
//      return ResponseEntity.ok(this.accountService.findByName(name));
        Account account = this.accountService.findByName(name);
        // Account -> AccountDTO
        AccountDTO accountDTO = this.modelMapper.map(account, AccountDTO.class);
        return ResponseEntity.ok(accountDTO);
        //get data
    }

    @PostMapping
    // [POST] http://localhost:8001/api/v1/accounts {}
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO login) {
        Optional<LoginResponseDTO> loginResponse = this.accountService.login(login.getUsername(), login.getPassword());
        return loginResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
