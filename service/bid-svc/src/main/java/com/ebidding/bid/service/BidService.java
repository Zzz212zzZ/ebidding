package com.ebidding.bid.service;

import com.ebidding.account.api.Account;
import com.ebidding.account.api.AccountClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidService {
    @Autowired
    private AccountClient accountClient;

    public Account findByName(String inputName) {
        return this.accountClient.getAccount(inputName).getBody();
    }
}