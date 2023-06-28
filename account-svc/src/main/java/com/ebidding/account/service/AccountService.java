package com.ebidding.account.service;

import com.ebidding.account.domain.Account;
import com.ebidding.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
//    @Autowired
    private final AccountRepository accountRepository;

    public Account findByName(String inputName) {
        return this.accountRepository.findByName(inputName).orElse(null);
    }
}
