package com.nibm.smartmedicine.service.impl;

import com.nibm.smartmedicine.entity.Patient;
import com.nibm.smartmedicine.repository.PatientRepository;
import com.nibm.smartmedicine.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

    public PatientServiceImpl(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Patient> getById(Long patientId) {
        return repository.findById(patientId);
    }

    @Override
    public Patient save(Patient patient) {
        return repository.save(patient);
    }

    @Override
    public Optional<Patient> getByUserId(Long id) {
        return repository.findByUserId(id);
    }
}
