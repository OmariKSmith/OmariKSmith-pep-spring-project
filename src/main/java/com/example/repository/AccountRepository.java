package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;
import java.util.Optional;
public interface AccountRepository extends JpaRepository<Account,Integer>{
    @Override
    Optional<Account> findById(Integer integer);
    Optional<Account> findByUsername(String username);
    Optional<Account> findByUsernameAndPassword(String username, String password);
}
