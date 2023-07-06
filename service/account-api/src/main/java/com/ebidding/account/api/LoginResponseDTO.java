package com.ebidding.account.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    @Column(name = "account_id", nullable = false)
    private String id;
    private String name;
    private String role;
    private String token;
}
