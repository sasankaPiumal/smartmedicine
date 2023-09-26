package com.nibm.smartmedicine.dto.response.user;

import com.nibm.smartmedicine.entity.User;
import com.nibm.smartmedicine.entity.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.AuthProvider;

@Data
@NoArgsConstructor
public class AuthUserResponse {
    private Long id;
    private String name;
    private String email;
    private String mobileNumber;
    private boolean emailVerified;
    private AuthProvider provider;
    private UserRole role;
    private boolean enabled;

    public AuthUserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.mobileNumber = user.getMobileNumber();
        this.emailVerified = user.getEmailVerified();
        this.role = user.getRole();
        this.enabled = user.isEnabled();
    }
}
