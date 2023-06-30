package com.ebidding.account.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Id;

//http://localhost:8001/api/vi/accounts?name=${inputName}
@FeignClient(name = "account-service",path = "api/v1/accounts",url = "${ebidding.account-endpoint}")
public interface AccountClient {
    @GetMapping("/")
    ResponseEntity<AccountDTO> getAccount(@RequestParam(name = "Account_ID") String ID);
}
