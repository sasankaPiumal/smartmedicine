package com.nibm.smartmedicine.dto.request.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RenewPasswordRequest {
    private String renewToken;
    private String newPassword;
}
