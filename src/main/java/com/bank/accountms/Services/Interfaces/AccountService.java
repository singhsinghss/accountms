package com.bank.accountms.Services.Interfaces;

import com.bank.accountms.Model.Account;

import java.util.List;

public interface AccountService {

    Account createAccount(Account account);
    Account getAccountsByUserId(Long userId);
    Account getAccountById(Long accountId);
    List<Account> getAccounts();
    Account partialUpdateAccount(Long accountId,Account account);
    Account updateAccountByAcc_User_id(Long accountId, Long userId,Account account);
    boolean deleteAccByAccId(Long accountId);
}
