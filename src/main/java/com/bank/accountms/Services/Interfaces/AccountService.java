package com.bank.accountms.Services.Interfaces;

import com.bank.accountms.Model.Account;

import java.util.List;

public interface AccountService {

    Account createAccount(Account account);
    Account getAccountsByUserId(Long user_id);
    Account getAccountById(Long account_id);
    List<Account> getAccounts();
    Account partialUpdateAccount(Long acc_id,Account account);
    Account updateAccountByAcc_User_id(Long acc_id, Long user_id,Account account);
}
