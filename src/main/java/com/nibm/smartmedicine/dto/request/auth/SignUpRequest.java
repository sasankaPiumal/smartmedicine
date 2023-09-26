package com.nibm.smartmedicine.dto.request.auth;

import com.nibm.smartmedicine.entity.Doctor;
import com.nibm.smartmedicine.entity.Patient;
import com.nibm.smartmedicine.entity.Pharmacy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String mobileNumber;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private String addressLine1;

    private String addressLine2;

    private String gender;

    private String specialNote;

    private LocalDate birthday;

    public Doctor getDoctor() {
        Doctor doctor = new Doctor();
        doctor.setEmail(this.email);
        doctor.setMobileNumber(this.mobileNumber);
        doctor.setName(this.name);
        return doctor;
    }

    public Patient getPatient() {
        Patient patient = new Patient();
        patient.setEmail(this.email);
        patient.setMobileNumber(this.mobileNumber);
        patient.setName(this.name);
        patient.setAddressLine1(this.addressLine1);
        patient.setAddressLine2(this.addressLine2);
        patient.setGender(this.gender);
        patient.setBirthday(this.birthday);
        patient.setSpecialNote(this.specialNote);
        return patient;
    }

    public Pharmacy getPharmacy() {
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setEmail(this.email);
        pharmacy.setContactNumber(this.mobileNumber);
        pharmacy.setName(this.name);
        return pharmacy;
    }
}
