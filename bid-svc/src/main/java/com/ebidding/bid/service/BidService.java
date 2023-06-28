package com.ebidding.bid.service;

import com.ebidding.account.api.AccountDTO;
import com.ebidding.account.api.AccountClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidService {
    private final AccountClient accountClient;


    public AccountDTO findByName(String inputName) {
        return this.accountClient.getAccount(inputName).getBody();
    }
}
