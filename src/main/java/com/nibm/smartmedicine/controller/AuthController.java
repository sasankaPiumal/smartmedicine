package com.nibm.smartmedicine.controller;


import com.nibm.smartmedicine.dto.request.auth.ChangePasswordRequest;
import com.nibm.smartmedicine.dto.request.auth.SignInRequest;
import com.nibm.smartmedicine.dto.request.auth.SignUpRequest;
import com.nibm.smartmedicine.dto.request.auth.UserUpdateRequest;
import com.nibm.smartmedicine.dto.response.ApiResponse;
import com.nibm.smartmedicine.dto.response.SignInResponse;
import com.nibm.smartmedicine.dto.response.user.AuthUserResponse;
import com.nibm.smartmedicine.entity.*;
import com.nibm.smartmedicine.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final UserService userService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final PharmacyService pharmacyService;

    public AuthController(AuthService authService, UserService userService, DoctorService doctorService, PatientService patientService, PharmacyService pharmacyService) {
        this.authService = authService;
        this.userService = userService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.pharmacyService = pharmacyService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        logger.info("signIn:: request:{}", signInRequest.toString());
        SignInResponse response = authService.signIn(signInRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("{userRole}/sign-up")
    public ResponseEntity<?> signUp(
            @Valid @RequestBody SignUpRequest signUpRequest,
            @PathVariable UserRole userRole) {

        logger.info("signIn:: request:{}", signUpRequest.toString());

        User user = authService.signUp(signUpRequest, userRole);

        if(userRole == UserRole.DOCTOR) {
            Doctor doctor = signUpRequest.getDoctor();
            doctor.setUser(user);
            doctorService.save(doctor);
        }

        if(userRole == UserRole.PATIENT) {
            Patient patient = signUpRequest.getPatient();
            patient.setUser(user);
            patientService.save(patient);
        }

        if(userRole == UserRole.PHARMACY) {
            Pharmacy pharmacy = signUpRequest.getPharmacy();
            pharmacy.setUser(user);
            pharmacyService.save(pharmacy);
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successful !"));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse> updateCurrentUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        logger.info("updateCurrentUser:: request:{}", userUpdateRequest.toString());
        userService.updateUser(userUpdateRequest);
        return ResponseEntity.ok(new ApiResponse(true, "User Updated Successful !"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        return ResponseEntity.ok(new AuthUserResponse(authService.findCurrentUser()));
    }

    /**
     * change current user password
     *
     * @param changePasswordRequest
     * @return
     */
    @PutMapping("/me/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        logger.info("changePassword:: request:{}", changePasswordRequest.toString());
        authService.changePassword(changePasswordRequest);
        return ResponseEntity.ok().build();
    }
}
