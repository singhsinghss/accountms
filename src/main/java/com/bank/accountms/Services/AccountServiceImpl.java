package com.bank.accountms.Services;

import com.bank.accountms.Model.Account;
import com.bank.accountms.Repositories.AccountRepository;
import com.bank.accountms.Services.Interfaces.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Component
public class AccountServiceImpl implements AccountService {
    @Autowired
    private final AccountRepository accountRepository;


    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

   @Override
    public Account getAccountsByUserId(Long userId) {
        Optional<Account> account=accountRepository.findByUserId(userId);
        if(account.isPresent())
        {
            return account.get();
        }
        else
        {
            throw new RuntimeException("Account not found with user id: "+userId);
        }
    }

    @Override
    public Account getAccountById(Long id) {
        Optional<Account> accountId=accountRepository.findById(id);
        if(accountId.isPresent())
        {
            return accountId.get();
        }
        else
        {
            throw new RuntimeException("Account not found with account id: "+id);
        }
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account partialUpdateAccount(Long accountId, Account account) {
        Optional<Account> existingAccId=accountRepository.findById(accountId);
        if(existingAccId.isPresent())
        {
            Account acc=existingAccId.get();
            //acc.setAccountId(account.getAccountId());
            if(account.getBalance()!=null) {
                acc.setBalance(account.getBalance());
            }
            if(account.getAccount_type()!=null) {
                acc.setAccount_type(account.getAccount_type());
            }
            if(account.getCurrency_type()!=null) {
                acc.setCurrency_type(account.getCurrency_type());
            }
            //acc.setCreatedAt(LocalDateTime.now());
            return accountRepository.save(acc);
        }
        else
        {
            throw new RuntimeException("Account not found with id: "+accountId);
        }
    }

    @Override
    public Account updateAccountByAcc_User_id(Long user_id,Long acc_id,Account account) {
        Account existingAccount = accountRepository.findById(acc_id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Account existingUser=accountRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User id not found"));
        existingAccount.setAccount_type(account.getAccount_type());
        System.out.println(account.getAccount_type());
        existingAccount.setBalance(account.getBalance());
        System.out.println(account.getBalance());
        existingAccount.setCurrency_type(account.getCurrency_type());
        return accountRepository.save(existingAccount);

    }


}
