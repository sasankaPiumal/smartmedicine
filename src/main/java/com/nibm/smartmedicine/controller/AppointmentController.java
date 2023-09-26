package com.nibm.smartmedicine.controller;

import com.nibm.smartmedicine.dto.request.appointment.AddAppointmentRequestDto;
import com.nibm.smartmedicine.dto.request.appointment.AddMedicineRequest;
import com.nibm.smartmedicine.dto.response.ApiResponse;
import com.nibm.smartmedicine.dto.response.appointment.AppointmentResponse;
import com.nibm.smartmedicine.entity.Appointment;
import com.nibm.smartmedicine.entity.AppointmentMedicine;
import com.nibm.smartmedicine.entity.AppointmentStatus;
import com.nibm.smartmedicine.repository.AppointmentMedicineRepository;
import com.nibm.smartmedicine.service.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/v1/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    private final AppointmentMedicineRepository appointmentMedicineRepository;

    public AppointmentController(AppointmentService appointmentService, AppointmentMedicineRepository appointmentMedicineRepository) {
        this.appointmentService = appointmentService;
        this.appointmentMedicineRepository = appointmentMedicineRepository;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> save(
            @RequestBody @Valid AddAppointmentRequestDto request) {

        ApiResponse<AppointmentResponse> response = new ApiResponse<>();

        try {

            Appointment lastRecord = appointmentService.getLastRecord();

            Appointment appointment = appointmentService.save(request.getAppointment(lastRecord));

            response.setCode(201);
            response.setSuccess(true);
            response.setDatetime(LocalDateTime.now());
            response.setData(new AppointmentResponse(appointment));
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

    @PutMapping("/medicine/update")
    public ResponseEntity<ApiResponse<String>> updateMedicines(
            @RequestBody @Valid AddMedicineRequest request) {

        ApiResponse<String> response = new ApiResponse<>();

        try {

            appointmentService.updateMedicines(request);

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

    @PutMapping("/mark-as-done")
    public ResponseEntity<ApiResponse<String>> markAsDone(
            @RequestBody @Valid AddMedicineRequest request) {

        ApiResponse<String> response = new ApiResponse<>();

        try {

            appointmentService.markAsDone(request);

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

    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<Appointment>>> get(
            @RequestParam("pageIndex") Integer pageIndex,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "doctorId", required = false) Long doctorId,
            @RequestParam(value = "patientId", required = false) Long patientId,
            @RequestParam(value = "pharmacyId", required = false) Long pharmacyId,
            @RequestParam(value = "status", required = false) AppointmentStatus appointmentStatus,
            @RequestParam("searchValue") String searchValue) {

        ApiResponse<Page<Appointment>> response = new ApiResponse<>();

        try {

            Page<Appointment> appointmentPage = appointmentService.getPage(
                    PageRequest.of(pageIndex, pageSize, Sort.by("createdDate").descending()),
                    searchValue,
                    appointmentStatus,
                    doctorId,
                    patientId,
                    pharmacyId);

            response.setCode(201);
            response.setSuccess(true);
            response.setDatetime(LocalDateTime.now());
            response.setData(appointmentPage);
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
    public ResponseEntity<ApiResponse<Appointment>> getById(
            @PathVariable("id") Long id) {

        ApiResponse<Appointment> response = new ApiResponse<>();

        try {

            Optional<Appointment> appointment = appointmentService.getById(id);

            if(!appointment.isPresent()) {
                response.setCode(404);
                response.setSuccess(false);
                response.setDatetime(LocalDateTime.now());
                response.setMessage("Not found !");
            }

            List<AppointmentMedicine> allByAppointmentId = appointmentMedicineRepository.findAllByAppointmentId(id);

            appointment.get().setAppointmentMedicines(allByAppointmentId);

            response.setCode(201);
            response.setSuccess(true);
            response.setDatetime(LocalDateTime.now());
            response.setData(appointment.get());
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
