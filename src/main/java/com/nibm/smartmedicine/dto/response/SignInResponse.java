package com.nibm.smartmedicine.dto.response;

import com.nibm.smartmedicine.entity.User;
import com.nibm.smartmedicine.entity.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.AuthProvider;

@Data
@NoArgsConstructor
public class SignInResponse {
    private Long id;
    private String name;
    private String email;
    private boolean emailVerified;
    private AuthProvider provider;
    private UserRole role;
    private boolean enabled;
    private String tokenType;
    private String accessToken;

    public SignInResponse(User user, String tokenType, String accessToken) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.emailVerified = user.getEmailVerified();
        this.role = user.getRole();
        this.enabled = user.isEnabled();
        this.tokenType = tokenType;
        this.accessToken = accessToken;
    }
}
