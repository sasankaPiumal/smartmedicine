package com.nibm.smartmedicine.service;


import com.nibm.smartmedicine.dto.request.auth.RenewPasswordRequest;
import com.nibm.smartmedicine.dto.request.auth.UserUpdateRequest;
import com.nibm.smartmedicine.entity.User;
import com.nibm.smartmedicine.entity.UserRole;
import com.nibm.smartmedicine.entity.UserStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    void updateUser(UserUpdateRequest request);

    void renewPassword(RenewPasswordRequest request);

    User findById(Long id);

    void changeStatus(Long id, UserStatus status);

    Page<User> filter(String searchValue, UserStatus status, Integer pageIndex, Integer pageSize, UserRole role);

    List<User> findAll(String searchValue);

    Long getUserCount(UserStatus status);
}
