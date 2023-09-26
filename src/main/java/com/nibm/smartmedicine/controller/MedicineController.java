package com.nibm.smartmedicine.controller;

import com.nibm.smartmedicine.dto.request.medicine.MedicineAddRequestDto;
import com.nibm.smartmedicine.dto.response.ApiResponse;
import com.nibm.smartmedicine.entity.Medicine;
import com.nibm.smartmedicine.service.MedicineService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/v1/medicine")
public class MedicineController {

    private final MedicineService service;

    public MedicineController(MedicineService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Medicine>>> getAll() {

        ApiResponse<List<Medicine>> response = new ApiResponse<>();

        try {

            List<Medicine> medicines = service.findAll();

            response.setCode(200);
            response.setSuccess(true);
            response.setDatetime(LocalDateTime.now());
            response.setData(medicines);
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
    public ResponseEntity<ApiResponse<Page<Medicine>>> get(
            @RequestParam("pageIndex") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "searchValue", required = false) String searchKey) {

        ApiResponse<Page<Medicine>> response = new ApiResponse<>();

        try {

            Page<Medicine> medicinePage = service.getPage(
                    PageRequest.of(pageNumber, pageSize),
                    searchKey);

            response.setCode(201);
            response.setSuccess(true);
            response.setDatetime(LocalDateTime.now());
            response.setData(medicinePage);
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

    @PostMapping
    public ResponseEntity<ApiResponse<Medicine>> save(
            @RequestBody MedicineAddRequestDto requestDto) {

        ApiResponse<Medicine> response = new ApiResponse<>();

        try {

            Medicine medicine = service.save(requestDto.getEntity());

            response.setCode(201);
            response.setSuccess(true);
            response.setDatetime(LocalDateTime.now());
            response.setData(medicine);
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

    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> delete(
            @RequestParam("id") Long id) {

        ApiResponse<String> response = new ApiResponse<>();

        try {

            service.remove(id);

            response.setCode(201);
            response.setSuccess(true);
            response.setDatetime(LocalDateTime.now());
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
