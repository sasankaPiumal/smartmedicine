package com.nibm.smartmedicine.dto.response.appointment;

import com.nibm.smartmedicine.entity.Appointment;
import com.nibm.smartmedicine.entity.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {

    private Long id;

    private String appointmentId;

    private String diseaseNote;

    private AppointmentStatus status;

    private Date createdDate;

    public AppointmentResponse(Appointment appointment) {
        this.id = appointment.getId();
        this.appointmentId = appointment.getAppointmentId();
        this.diseaseNote = appointment.getDiseaseNote();
        this.status = appointment.getAppointmentStatus();
        this.createdDate = appointment.getCreatedDate();
    }

}
