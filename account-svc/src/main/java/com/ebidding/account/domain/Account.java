package com.ebidding.account.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor 注解是针对无参数的构造函数，它会生成一个无参数的构造方法。
//@AllArgsConstructor 会生成一个包含所有变量的构造方法。
@Builder
@Entity
public class Account {
    @Id
    //@Id标注用于声明一个实体类的属性映射为数据库的主键列
    @Column(name = "account_id", nullable = false)
    //id位主键，不能为空
    private Long accountId;

    private String name;

    @Enumerated(EnumType.STRING)
    private AccountRole role;

    private String password_hash;
}
