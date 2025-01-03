package com.bank.accountms.Services;

import com.bank.accountms.Model.Account;
import com.bank.accountms.Model.UserProfileDTO;
import com.bank.accountms.Repositories.AccountRepository;
import com.bank.accountms.Services.Interfaces.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

@Component
public class AccountServiceImpl implements AccountService {
    @Autowired
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


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
    public Account getAccountById(Long accountId) {
        Optional<Account> account_Id=accountRepository.findById(accountId);
        if(account_Id.isPresent())
        {
            return account_Id.get();
        }
        else
        {
            throw new RuntimeException("Account not found with account id: "+accountId);
        }
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account partialUpdateAccount(Long accountId, Account account) {
        Optional<Account> existingAccId=accountRepository.findByAccountId(accountId);
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
           /* if(account.getTransaction_limit()!=null)
            {
                acc.setTransaction_limit(account.getTransaction_limit());
            }*/
            //acc.setCreatedAt(LocalDateTime.now());
            return accountRepository.save(acc);
        }
        else
        {
            throw new RuntimeException("Account not found with id: "+accountId);
        }
    }

    @Override
    public Account updateAccountByAcc_User_id(Long userId,Long accountId,Account account) {
        Account existingAccount = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Account existingUser=accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User id not found"));
        existingAccount.setAccount_type(account.getAccount_type());
        System.out.println(account.getAccount_type());
        existingAccount.setBalance(account.getBalance());
        System.out.println(account.getBalance());
        existingAccount.setCurrency_type(account.getCurrency_type());
        return accountRepository.save(existingAccount);


    }

    @Override
    public boolean deleteAccByAccId(Long accountId) {
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        if (account.isEmpty()) {
            throw new RuntimeException("Account not found for accountId: " + accountId);
        }
        accountRepository.deleteById(accountId);
        return true;

    }


}
