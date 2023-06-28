package com.ebidding.bwic.service.service;

import com.ebidding.bwic.service.domain.Account;
import com.ebidding.bwic.service.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account findByName(String inputName) {
        return this.accountRepository.findByName(inputName).orElse(null);
    }
}
