package com.nibm.smartmedicine.dto.request.appointment;

import com.nibm.smartmedicine.entity.AppointmentMedicine;
import com.nibm.smartmedicine.entity.Medicine;
import lombok.Data;

import java.util.Date;

@Data
public class AppointmentMedicineRequest {

    private String dose;

    private Long medicineId;

    public AppointmentMedicine getAppointmentMedicine(){
        AppointmentMedicine appointmentMedicine = new AppointmentMedicine();
        Medicine medicine = new Medicine();
        medicine.setId(this.medicineId);
        appointmentMedicine.setMedicine(medicine);
        appointmentMedicine.setDose(dose);
        appointmentMedicine.setCreatedDate(new Date());
        return appointmentMedicine;
    }
}
