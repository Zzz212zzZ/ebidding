package com.ebidding.account.service;

import com.ebidding.account.api.AccountDTO;
import com.ebidding.account.domain.Account;
import com.ebidding.account.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    @Test
    public void TestAccountService(){
        // step 1: prepare test data
        Account account = Account.builder().id("1").build();
        // step 2: mock interface
        AccountRepository repository = mock(AccountRepository.class);
        when(repository.findByName("tyx")).thenReturn(Optional.of(account));
        // step 3: init test instance
        AccountService accountService = new AccountService(repository);
        // step 4: test and assert
        assertThat(accountService.findByName("tyx")).isEqualTo(account);
    }
}
