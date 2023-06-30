package com.ebidding.service.domain;

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
    @Column(name = "accountId", nullable = false)
    private String accountId;

    private String name;

    @Enumerated(EnumType.STRING)
    private AccountRole role;

    private String passwordHash;

}

