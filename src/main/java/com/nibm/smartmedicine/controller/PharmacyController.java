package com.nibm.smartmedicine.controller;

import com.nibm.smartmedicine.dto.response.ApiResponse;
import com.nibm.smartmedicine.entity.Pharmacy;
import com.nibm.smartmedicine.service.PharmacyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/v1/pharmacy")
public class PharmacyController {

    private final PharmacyService service;

    public PharmacyController(PharmacyService pharmacyService) {
        this.service = pharmacyService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Pharmacy>>> getAll() {

        ApiResponse<List<Pharmacy>> response = new ApiResponse<>();

        try {

            List<Pharmacy> pharmacies = service.findAll();

            response.setCode(200);
            response.setSuccess(true);
            response.setDatetime(LocalDateTime.now());
            response.setData(pharmacies);
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

    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<Pharmacy>>> get(
            @RequestParam("pageIndex") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("searchValue") String searchKey) {

        ApiResponse<Page<Pharmacy>> response = new ApiResponse<>();

        try {

            Page<Pharmacy> pharmacyPage = service.getPage(
                    PageRequest.of(pageNumber, pageSize),
                    searchKey);

            response.setCode(201);
            response.setSuccess(true);
            response.setDatetime(LocalDateTime.now());
            response.setData(pharmacyPage);
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
}
