package com.example.service;

import org.springframework.data.domain.Example;
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

    public Optional<Account> register(Account newAccount){
        Optional<Account> account = accountRepository.findByUsernameAndPassword(newAccount.getUsername(),newAccount.getPassword());
        if(account.isEmpty()){
          return  Optional.of(accountRepository.save(newAccount));
        }
        return Optional.empty();
    }
    public Optional<Account> findBy(Account account){
        return  accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
    public Optional<Account> login(Account userAccount){
            return accountRepository.findByUsernameAndPassword(userAccount.getUsername(), userAccount.getPassword());
    }

    public boolean registrationValidation(Account newAccount){
        if(!newAccount.getUsername().isBlank() && !(newAccount.getPassword().length() >=4)  ){
            return true;
        }
        return false;
    }

    public void save(Account newAccount) {
        accountRepository.save(newAccount);
    }
}
