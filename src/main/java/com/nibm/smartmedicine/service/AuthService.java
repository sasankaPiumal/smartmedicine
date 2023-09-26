package com.nibm.smartmedicine.service;

import com.nibm.smartmedicine.dto.request.auth.ChangePasswordRequest;
import com.nibm.smartmedicine.dto.request.auth.SignInRequest;
import com.nibm.smartmedicine.dto.request.auth.SignUpRequest;
import com.nibm.smartmedicine.dto.response.SignInResponse;
import com.nibm.smartmedicine.entity.User;
import com.nibm.smartmedicine.entity.UserRole;
import org.springframework.security.core.Authentication;

public interface AuthService {
    SignInResponse signIn(SignInRequest request);

    Authentication signIn(String userName, String password);

    User signUp(SignUpRequest request, UserRole role);

    User findCurrentUser();

    void changePassword(ChangePasswordRequest request);
}
