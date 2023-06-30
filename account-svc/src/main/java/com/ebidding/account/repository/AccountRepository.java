package com.ebidding.account.repository;

import com.ebidding.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    // SELECT ... FROM Account Where NAME = ${inputName}
    // 从数据库中读取数据存入Optional中
    Optional<Account> findByName(String inputName);
}
