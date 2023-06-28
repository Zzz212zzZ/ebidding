package com.ebidding.bwic.bid.service;

import com.ebidding.bwic.account.api.AccountDTO;
import com.ebidding.bwic.account.api.AccountClient;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BidService {
    private final AccountClient accountClient;


    public AccountDTO findByName(String inputName) {
        AccountDTO res = this.accountClient.getAccount(inputName).getBody();
//        if ("Kuo".equals(inputName)) {
//            res.setName(res.getName().toUpperCase());
//        }
        return res;
    }
}