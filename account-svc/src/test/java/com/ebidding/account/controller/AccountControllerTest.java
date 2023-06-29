package com.ebidding.account.controller;

import com.ebidding.account.domain.Account;
import com.ebidding.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AccountController accountController;

    @Autowired
    private AccountService accountService;

    @Test
    public void TestAccountController(){
        // step 1: prepare test data
        Account account = Account.builder().id("1").build();
        // step 2: mock interface
        AccountService mock = org.mockito.Mockito.mock(AccountService.class);
        when(mock.findByName("tyx")).thenReturn(account);
//        when(accountService.findByName("tyx")).thenReturn(account);
        // step 3: init test instance
        // step 4: test and assert
        assertThat(accountController).isNotNull();
    }

    @Test
    public void TestAccountControllerWithMvc() throws Exception {
        Account account = Account.builder().id("1").build();
        AccountService mock = org.mockito.Mockito.mock(AccountService.class);
        when(mock.findByName("tyx")).thenReturn(account);
//        when(accountService.findByName("tyx")).thenReturn(account);
        mvc.perform(get("/api/v1/accounts?name=tyx")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("1")));

    }

    @Test
    public void TestInvalidAccountControllerWithMvc() throws Exception {
        Account account = Account.builder().id("1").build();
        AccountService mock = org.mockito.Mockito.mock(AccountService.class);
        when(mock.findByName("tyx")).thenReturn(account);
//      when(accountService.findByName("tyx")).thenReturn(account);
//      报错：org.mockito.exceptions.misusing.MissingMethodInvocationException:
//      when() requires an argument which has to be 'a method call on a mock'.
//      For example: when(mock.getArticles()).thenReturn(articles);
        mvc.perform(get("/api/v1/bids?name=tyx")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
