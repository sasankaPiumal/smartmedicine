package com.nibm.smartmedicine.service.impl;

import com.nibm.smartmedicine.dto.request.auth.RenewPasswordRequest;
import com.nibm.smartmedicine.dto.request.auth.UserUpdateRequest;
import com.nibm.smartmedicine.entity.User;
import com.nibm.smartmedicine.entity.UserRole;
import com.nibm.smartmedicine.entity.UserStatus;
import com.nibm.smartmedicine.exception.ResourceNotFoundException;
import com.nibm.smartmedicine.repository.UserRepository;
import com.nibm.smartmedicine.repository.specification.UserSpecification;
import com.nibm.smartmedicine.security.TokenProvider;
import com.nibm.smartmedicine.service.AuthService;
import com.nibm.smartmedicine.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AuthService authService, TokenProvider tokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void updateUser(UserUpdateRequest request) {
        User user = userRepository.findById(authService.findCurrentUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", authService.findCurrentUser().getId()));
        user.setMobileNumber(request.getMobileNumber());
        user.setName(request.getName());
        userRepository.save(user);
    }

    @Override
    public void renewPassword(RenewPasswordRequest request) {
        if (!tokenProvider.validateToken(request.getRenewToken())) {
            throw new AccessDeniedException("Invalid renewToken");
        }
        Long userId = tokenProvider.getUserIdFromToken(request.getRenewToken());
        User user = findById(userId);
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User was not found"));
    }

    @Override
    public void changeStatus(Long id, UserStatus status) {
        User user = findById(id);
        boolean enable = status == UserStatus.ENABLE;
        user.setEnabled(enable);
        userRepository.save(user);
    }

    @Override
    public Page<User> filter(String searchValue, UserStatus status, Integer pageIndex, Integer pageSize, UserRole role) {
        Pageable page = PageRequest.of(pageIndex, pageSize, Sort.by("id").descending());
        Specification<User> specification = Specification.where(UserSpecification.search(searchValue == null ? null : searchValue.trim()))
                .and(UserSpecification.isEnable(status)).and(UserSpecification.role(role));
        return userRepository.findAll(specification, page);
    }


    /**
     * get all users
     *
     * @param searchValue
     * @return
     */
    @Override
    public List<User> findAll(String searchValue) {
        Specification<User> specification = Specification.where(UserSpecification.search(searchValue));
        return userRepository.findAll(specification, Sort.by("name").ascending());
    }

    @Override
    public Long getUserCount(UserStatus status) {
        Long count;
        if (status.equals(UserStatus.ENABLE)) {
            count = userRepository.getUserCountByStatus(true);
        } else {
            count = userRepository.getUserCountByStatus(false);
        }
        return count;
    }
}
