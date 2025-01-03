package com.bank.accountms.Repositories;

import com.bank.accountms.Model.Account;
import com.bank.accountms.Model.UserProfileDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    //Optional<Account> findByAccountId(Long account_id);
   // Optional<Account> findByAccountId(Long accountId);
    Optional<Account> findByUserId(Long userId);
    Optional<Account> findByAccountId(Long accountId);


}
