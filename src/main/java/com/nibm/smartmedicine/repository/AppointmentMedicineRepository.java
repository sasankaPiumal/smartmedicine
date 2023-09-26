package com.nibm.smartmedicine.repository;

import com.nibm.smartmedicine.entity.AppointmentMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentMedicineRepository extends JpaRepository<AppointmentMedicine, Long> {
    List<AppointmentMedicine> findAllByAppointmentId(Long id);
}
