package com.bank.accountms.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {

    private Long user_id;
    private String username;
    private String password_hash;
    private String email;
    private String phone_number;
    private boolean two_factor_enabled;
    private String kyc_status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
