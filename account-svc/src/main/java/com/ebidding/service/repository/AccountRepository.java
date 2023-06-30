package com.ebidding.service.repository;

import com.ebidding.service.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    // SELECT ... FROM Account Where NAME = ${inputName}
    Optional<Account> findById(int inputId);

    Optional<Account> findByName(String inputName);

}

