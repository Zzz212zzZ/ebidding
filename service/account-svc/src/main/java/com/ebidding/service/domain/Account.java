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
    @Column(name = "id", nullable = false)
    private String id;

    private String name;
    private String password_hash;

    @Enumerated(EnumType.STRING)
    private AccountRole role;

}
