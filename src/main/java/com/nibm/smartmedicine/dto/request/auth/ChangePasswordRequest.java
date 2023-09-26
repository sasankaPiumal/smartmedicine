package com.nibm.smartmedicine.dto.request.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class ChangePasswordRequest {
    @NotEmpty(message = "Current password required")
    String currentPassword;
    @NotEmpty(message = "New password required")
    String newPassword;
}
