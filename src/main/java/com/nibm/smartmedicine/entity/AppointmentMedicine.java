package com.nibm.smartmedicine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "APPOINTMENT_MEDICINE")
public class AppointmentMedicine extends BaseEntity {

    @Column(name = "dose")
    private String dose;

    @JsonIgnore
    @ManyToOne
    private Appointment appointment;

    @ManyToOne
    private Medicine medicine;

}
