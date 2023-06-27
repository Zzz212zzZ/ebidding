package com.ebidding.service.service;

import com.ebidding.service.domain.Account;
import com.ebidding.service.repository.AccountRepository;
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

