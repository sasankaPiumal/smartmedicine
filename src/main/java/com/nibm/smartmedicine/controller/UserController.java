package com.nibm.smartmedicine.controller;

import com.nibm.smartmedicine.dto.request.auth.SignUpRequest;
import com.nibm.smartmedicine.dto.response.ApiResponse;
import com.nibm.smartmedicine.dto.response.user.AuthUserResponse;
import com.nibm.smartmedicine.dto.response.user.UserResponse;
import com.nibm.smartmedicine.entity.User;
import com.nibm.smartmedicine.entity.UserRole;
import com.nibm.smartmedicine.entity.UserStatus;
import com.nibm.smartmedicine.security.CurrentAuth;
import com.nibm.smartmedicine.service.AuthService;
import com.nibm.smartmedicine.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/v1/admin/user")
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    /**
     * get all
     *
     * @param searchValue
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAll(@RequestParam(required = false) String searchValue) {
        CurrentAuth.throwNotVerified();
        List<UserResponse> userResponses = userService.findAll(searchValue)
                .stream().map(UserResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(userResponses);
    }


    /**
     * get user by id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        CurrentAuth.throwNotVerified();
        logger.info("getById:: id:{}, scope:{}", id);
        User user = userService.findById(id);
        return ResponseEntity.ok(new AuthUserResponse(user));
    }

    /**
     * change user status
     *
     * @param id
     * @param status
     * @return
     */
    @PutMapping("/change-status/{id}")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @RequestParam UserStatus status) {
        CurrentAuth.throwNotVerified();
        logger.info("changeStatus:: id:{}, status:{}", id, status);
        userService.changeStatus(id, status);
        return ResponseEntity.ok().build();
    }
}
