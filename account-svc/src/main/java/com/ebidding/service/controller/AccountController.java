package com.ebidding.service.controller;

import com.ebidding.account.api.AccountDTO;
import com.ebidding.account.api.LoginRequestDTO;
import com.ebidding.account.api.LoginResponseDTO;
import com.ebidding.service.domain.Account;
import com.ebidding.service.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping()
    // PathVariable v.s. RequestParam v.s. RequestBody
    // http://localhost:8001/api/v1/accounts?name={inputName}
    // http://localhost:8001/api/v1/accounts/1
    // [GET] http://localhost:8001/api/v1/accounts {}
    public ResponseEntity<AccountDTO> getAccount(@RequestParam("name") String name) {
        Account account = this.accountService.findByName(name);
        // Account -> AccountDTO
        AccountDTO accountDTO = this.modelMapper.map(account, AccountDTO.class);
        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping()
    // [POST] http://localhost:8001/api/v1/accounts {}
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO login) {
        Optional<LoginResponseDTO> loginResponse = this.accountService.login(login.getUsername(), login.getPassword());
        return loginResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
