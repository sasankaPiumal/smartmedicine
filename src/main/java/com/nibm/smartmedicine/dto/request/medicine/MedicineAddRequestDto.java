package com.nibm.smartmedicine.dto.request.medicine;

import com.nibm.smartmedicine.entity.Medicine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineAddRequestDto {

    private String name;

    private String note;

    public Medicine getEntity() {
        Medicine medicine = new Medicine();
        medicine.setName(this.name);
        medicine.setNote(this.note);
        return medicine;
    }

}
