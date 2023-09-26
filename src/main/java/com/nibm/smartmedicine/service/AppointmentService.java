package com.nibm.smartmedicine.service;

import com.nibm.smartmedicine.dto.request.appointment.AddMedicineRequest;
import com.nibm.smartmedicine.entity.Appointment;
import com.nibm.smartmedicine.entity.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface AppointmentService {

    Appointment save(Appointment appointment);

    Appointment getLastRecord();

    Page<Appointment> getPage(PageRequest of, String searchKey,
                              AppointmentStatus appointmentStatus, Long doctorId, Long patientId, Long pharmacyId);

    Optional<Appointment> getById(Long id);

    void updateMedicines(AddMedicineRequest request);

    void markAsDone(AddMedicineRequest request);
}
