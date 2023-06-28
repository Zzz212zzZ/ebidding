package com.ebidding.account.api;

import lombok.Data;

@Data
public class Account {
    private String id;

    private String name;

    private String role;

    private String password_hash;
}