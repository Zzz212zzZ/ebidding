package com.ebidding.bid.service;


import com.ebidding.account.api.AccountClient;
import com.ebidding.account.api.AccountDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BidServiceTest {
    @Test
    public void TestBidService() {
        // step 1: prepare test data
        AccountDTO accountDTO = AccountDTO.builder().id("1").build();
        // step 2: mock interface
        AccountClient client = mock(AccountClient.class);
        when(client.getAccount("Kuo")).thenReturn(ResponseEntity.ok(accountDTO));
        // step 3: init test instance
        BidService bidService = new BidService(client);
        // step 4: test and assert
        assertThat(bidService.findByName("Kuo")).isEqualTo(accountDTO);
//        assertThat(1+1).isEqualTo(3);
    }
}