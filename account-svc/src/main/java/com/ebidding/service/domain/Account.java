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
    //@Id标注用于声明一个实体类的属性映射为数据库的主键列
    @Column(name = "id", nullable = false)
    //id位主键，不能为空
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private AccountRole role;

    private String password_hash;
}
