package com.bank.accountms.Controller;

import com.bank.accountms.Model.Account;
import com.bank.accountms.Model.UserProfileDTO;
import com.bank.accountms.Model.UserProfileDTO;
import com.bank.accountms.Services.AccountServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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


public class AccountController {
    private static final Logger logger = LogManager.getLogger(AccountController.class);
    @Autowired
    private final AccountServiceImpl accServiceImpl;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.microservice.url}")
    private final String USER_MICROSERVICE_URL;
    @Autowired
    public AccountController(AccountServiceImpl accServiceImpl,
                             @Value("${user.microservice.url}") String userMicroserviceUrl) {
        this.accServiceImpl = accServiceImpl;
       // this.userDto = userDto;
        this.USER_MICROSERVICE_URL = userMicroserviceUrl;
    }
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Accountms is healthy");
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
            ResponseEntity<UserProfileDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, UserProfileDTO.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (HttpClientErrorException | HttpServerErrorException e)
        {
            return false;
        }
    }

    @GetMapping("/account/{accountId}")
    private ResponseEntity<Account> getAccById(@PathVariable Long accountId)
    {
        return new ResponseEntity<>(accServiceImpl.getAccountById(accountId),HttpStatus.OK);
    }

    @GetMapping("/{userId}/account")
    private ResponseEntity<Account> getAccByUserId(@PathVariable Long userId)
    {
        return new ResponseEntity<>(accServiceImpl.getAccountsByUserId(userId),HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<List<Account>> getAcc()
    {
        List<Account> accounts=accServiceImpl.getAccounts();
        return accounts.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(accounts);

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

    @PutMapping("/userid={userId}/accountid={accountId}")
    private ResponseEntity<Account> updateByUser_Acc_Id(@PathVariable Long userId,
                                                        @PathVariable Long accountId,
                                                        @RequestBody Account account)
    {
        return new ResponseEntity<>(accServiceImpl.updateAccountByAcc_User_id(
                userId,accountId,account),HttpStatus.OK);
    }
    @DeleteMapping("/account/{accountId}")
    public ResponseEntity<String> deleteAcc(@PathVariable Long accountId)
    {
        boolean isDeleted = accServiceImpl.deleteAccByAccId(accountId);
        return isDeleted ? ResponseEntity.ok("Account deleted successfully.") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found for id: "+accountId);
    }

    @ExceptionHandler
    public ResponseEntity<String> respondWithError(Exception e){
        logger.error("Exception Occurred. Details : {}", e.getMessage());
        return new ResponseEntity<>("Exception Occurred. More Info :"
                + e.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
