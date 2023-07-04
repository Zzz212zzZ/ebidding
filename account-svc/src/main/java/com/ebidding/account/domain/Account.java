package com.ebidding.account.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Account {

    @Id
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    private String name;
    private String password_hash;

    @Enumerated(EnumType.STRING)
    private AccountRole role;

}
