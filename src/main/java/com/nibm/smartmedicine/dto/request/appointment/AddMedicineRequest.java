package com.nibm.smartmedicine.dto.request.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMedicineRequest {

    private Long pharmacyId;

    private Long appointmentId;

    private List<AppointmentMedicineRequest> medicineRequests;

}
