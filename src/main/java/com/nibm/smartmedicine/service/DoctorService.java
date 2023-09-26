package com.nibm.smartmedicine.service;

import com.nibm.smartmedicine.entity.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {

    List<Doctor> findAll();

    Optional<Doctor> findById(Long id);

    Doctor save(Doctor doctor);

}
