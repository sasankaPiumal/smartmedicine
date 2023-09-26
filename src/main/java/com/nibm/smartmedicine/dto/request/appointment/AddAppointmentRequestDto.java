package com.nibm.smartmedicine.dto.request.appointment;

import com.nibm.smartmedicine.entity.Appointment;
import com.nibm.smartmedicine.entity.AppointmentStatus;
import com.nibm.smartmedicine.entity.Doctor;
import com.nibm.smartmedicine.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddAppointmentRequestDto {

    private Long patientId;

    private Long doctorId;

    private String diseaseNote;

    public Appointment getAppointment(Appointment lastAppointment) {
        Appointment appointment = new Appointment();

        Doctor doctor = new Doctor();
        doctor.setId(this.doctorId);
        appointment.setDoctor(doctor);

        Patient patient = new Patient();
        patient.setId(this.patientId);
        appointment.setPatient(patient);

        appointment.setDiseaseNote(diseaseNote);
        appointment.setCreatedDate(new Date());
        appointment.setAppointmentStatus(AppointmentStatus.PENDING);
        appointment.setAppointmentId(String.format("APP%s", lastAppointment != null ? (lastAppointment.getId()  + 1): 1));
        return appointment;
    }

}
