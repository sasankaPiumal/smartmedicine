package com.nibm.smartmedicine.service;

import com.nibm.smartmedicine.entity.Patient;
//import sun.security.krb5.internal.PAData;

import java.util.Optional;

public interface PatientService {

    Optional<Patient> getById(Long patientId);

    Patient save(Patient patient);

    Optional<Patient> getByUserId(Long id);
}
