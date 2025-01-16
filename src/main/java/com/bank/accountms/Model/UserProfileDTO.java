package com.bank.accountms.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class UserProfileDTO {

    private Long userId;
    private String username;
    private String password_hash;
    private String email;
    private String phone_number;
    private boolean two_factor_enabled;
    private String kyc_status;


}
