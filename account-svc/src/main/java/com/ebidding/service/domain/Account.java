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
    @Column(name = "account_id", nullable = false)
    private int id;

    private String name;

    @Enumerated(EnumType.STRING)
    private AccountRole role;

    private String passwordHash;

}
