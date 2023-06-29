package com.ebidding.account.controller;

import com.ebidding.account.api.AccountDTO;
import com.ebidding.account.domain.Account;
import com.ebidding.account.service.AccountService;
import org.modelmapper.ModelMapper;
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
        //取得了所需要的数据
    }
}
