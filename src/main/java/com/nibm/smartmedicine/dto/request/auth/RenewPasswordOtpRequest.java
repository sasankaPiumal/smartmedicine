package com.nibm.smartmedicine.dto.request.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RenewPasswordOtpRequest {
    private String email;
    private String otpCode;
}
