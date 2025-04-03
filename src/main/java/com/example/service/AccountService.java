package com.example.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<Account> register(Account newAccount){
        if(accountRepository.findByUsername(newAccount.getUsername()).isPresent()){
            return ResponseEntity.status(409).body(newAccount);
        }
        else if (registrationValidation(newAccount)) {
            return ResponseEntity.status(400).body(newAccount);
        }
        else {
            accountRepository.save(newAccount);
        }
        return ResponseEntity.status(200)
            .body(accountRepository.findByUsername(newAccount.getUsername()).get());
    }

    public ResponseEntity<Account> login(Account userAccount){
        Optional<Account> account = accountRepository.findByUsernameAndPassword(userAccount.getUsername(), userAccount.getPassword());
            return account.map(acc -> ResponseEntity
            .status(200)
            .body(acc))
            .orElseGet(() -> ResponseEntity.status(401).body(userAccount));
    }

    public boolean registrationValidation(Account newAccount){
        if(!newAccount.getUsername().isBlank() && !(newAccount.getPassword().length() >=4)  ){
            return true;
        }
        return false;
    }
}
