package com.ebidding.account.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private Long accountId;
    private String name;
    private String role;
    private String token;
}
