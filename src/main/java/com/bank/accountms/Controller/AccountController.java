package com.bank.accountms.Controller;

import com.bank.accountms.Model.Account;
import com.bank.accountms.Model.UserDTO;
import com.bank.accountms.Services.AccountServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")

@Component
public class AccountController {

    @Autowired
    private final AccountServiceImpl accServiceImpl;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.microservice.url}")
    private final String USER_MICROSERVICE_URL;

    public AccountController(AccountServiceImpl accServiceImpl,
                             @Value("${user.microservice.url}") String userMicroserviceUrl) {
        this.accServiceImpl = accServiceImpl;
        this.USER_MICROSERVICE_URL = userMicroserviceUrl;
    }

    @PostMapping("/new")
    private ResponseEntity<Account> createNewAccount(@RequestBody Account account)
    {
        boolean isValid=checkIfUserExists(account.getUserId());
        if(!isValid) {
            throw new RuntimeException("user not found with id: " + account.getUserId());
        }
        return new ResponseEntity<>(accServiceImpl.createAccount(account),HttpStatus.CREATED);
    }

    private boolean checkIfUserExists(Long userId) {

        try {
            String url = this.USER_MICROSERVICE_URL +"/"+ userId;
            ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (HttpClientErrorException | HttpServerErrorException e)
        {
            return false;
        }
    }

    @GetMapping("/account/{id}")
    private ResponseEntity<Account> getAccById(@PathVariable Long id)
    {
        return new ResponseEntity<>(accServiceImpl.getAccountById(id),HttpStatus.OK);
    }

    @GetMapping("/{user_id}/account")
    private ResponseEntity<Account> getAccByUserId(@PathVariable Long user_id)
    {
        return new ResponseEntity<>(accServiceImpl.getAccountsByUserId(user_id),HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<List<Account>> getAcc()
    {
        return new ResponseEntity<>(accServiceImpl.getAccounts(),HttpStatus.OK);
    }
    @PatchMapping("/{accountId}")
    private  ResponseEntity<Account> updateAccountById(@PathVariable Long accountId,@RequestBody Account account)
    {
        try
        {
            Account updateAcc=accServiceImpl.partialUpdateAccount(accountId, account);
            return ResponseEntity.ok(updateAcc);
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // acc not found, return 404
        }
    }

    @PutMapping("/userid={user_id}/accountid={account_id}")
    private ResponseEntity<Account> updateByUser_Acc_Id(@PathVariable Long user_id,
                                                        @PathVariable Long account_id,
                                                        @RequestBody Account account)
    {
        return new ResponseEntity<>(accServiceImpl.updateAccountByAcc_User_id(
                user_id,account_id,account),HttpStatus.OK);
    }


}
