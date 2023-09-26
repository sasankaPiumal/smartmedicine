package com.nibm.smartmedicine.service.impl;

import com.nibm.smartmedicine.dto.request.auth.ChangePasswordRequest;
import com.nibm.smartmedicine.dto.request.auth.SignInRequest;
import com.nibm.smartmedicine.dto.request.auth.SignUpRequest;
import com.nibm.smartmedicine.dto.response.SignInResponse;
import com.nibm.smartmedicine.entity.User;
import com.nibm.smartmedicine.entity.UserRole;
import com.nibm.smartmedicine.exception.BadRequestException;
import com.nibm.smartmedicine.exception.UnauthorizedException;
import com.nibm.smartmedicine.repository.UserRepository;
import com.nibm.smartmedicine.security.CurrentAuth;
import com.nibm.smartmedicine.security.TokenProvider;
import com.nibm.smartmedicine.security.UserPrincipal;
import com.nibm.smartmedicine.service.AuthService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public SignInResponse signIn(SignInRequest request) {
        Authentication authentication = signIn(request.getEmail(), request.getPassword());
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String token = tokenProvider.createToken(authentication);
        return new SignInResponse(userPrincipal.getUser(), "Bearer", token);
    }

    @Override
    public Authentication signIn(String userName, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userName,
                        password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    @Override
    public User signUp(SignUpRequest request, UserRole role) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }
        User user = new User();
        user.setName(request.getName());
        user.setMobileNumber(request.getMobileNumber());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findCurrentUser() {
        return CurrentAuth.getUser().orElseThrow(() -> new UnauthorizedException("Unauthorized"));
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        User user = findCurrentUser();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new AccessDeniedException("Invalid current password");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
