package com.ebidding.bwic.bid.controller;

import com.ebidding.bwic.account.api.AccountDTO;
import com.ebidding.bwic.bid.service.BidService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BidControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BidController bidController;

    @MockBean
    private BidService bidService;

    @Test
    public void TestBidController() {
        // step 1: prepare test data
        AccountDTO accountDTO = AccountDTO.builder().id("1").build();
        // step 2: mock interface
        when(bidService.findByName("Kuo")).thenReturn(accountDTO);
        // step 3: init test instance
        // step 4: test and assert
        assertThat(bidController).isNotNull();
    }

    @Test
    public void TestBidControllerWithMvc() throws Exception {
        // step 1: prepare test data
        AccountDTO accountDTO = AccountDTO.builder().id("1").build();
        // step 2: mock interface
        when(bidService.findByName("Kuo")).thenReturn(accountDTO);
        // step 3: init test instance
        // step 4: test and assert
        mvc.perform(get("/api/v1/bids?name=Kuo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("1")));
    }

    @Test
    public void TestInvalidBidControllerWithMvc() throws Exception {
        // step 1: prepare test data
        AccountDTO accountDTO = AccountDTO.builder().id("1").build();
        // step 2: mock interface
        when(bidService.findByName("Kuo")).thenReturn(accountDTO);
        // step 3: init test instance
        // step 4: test and assert
        mvc.perform(get("/api/v1/accounts?name=Kuo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}