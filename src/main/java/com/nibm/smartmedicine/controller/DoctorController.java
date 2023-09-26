package com.nibm.smartmedicine.controller;

import com.nibm.smartmedicine.dto.response.ApiResponse;
import com.nibm.smartmedicine.entity.Doctor;
import com.nibm.smartmedicine.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/v1/doctor")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService doctorService) {
        this.service = doctorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Doctor>>> getAll() {

        ApiResponse<List<Doctor>> response = new ApiResponse<>();

        try {

            List<Doctor> doctors = service.findAll();

            response.setCode(200);
            response.setSuccess(true);
            response.setDatetime(LocalDateTime.now());
            response.setData(doctors);
            response.setMessage("Success !");

        } catch (Exception exception) {
            exception.printStackTrace();
            response.setCode(500);
            response.setDatetime(LocalDateTime.now());
            response.setSuccess(false);
            response.setMessage(exception.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Doctor>> getById(@PathVariable Long id) {

        ApiResponse<Doctor> response = new ApiResponse<>();

        try {

            Optional<Doctor> doctor = service.findById(id);

            if(doctor.isPresent()) {
                response.setCode(200);
                response.setSuccess(true);
                response.setDatetime(LocalDateTime.now());
                response.setData(doctor.get());
                response.setMessage("Success !");
            } else {
                response.setCode(404);
                response.setSuccess(true);
                response.setDatetime(LocalDateTime.now());
                response.setMessage("not found !");
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            response.setCode(500);
            response.setDatetime(LocalDateTime.now());
            response.setSuccess(false);
            response.setMessage(exception.getMessage());
        }

        return ResponseEntity.ok(response);
    }

}
