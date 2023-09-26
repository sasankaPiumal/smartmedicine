package com.nibm.smartmedicine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "APPOINMENTS")
public class Appointment extends BaseEntity {

    @Column(name = "appointment_id")
    private String appointmentId;

    @Column(name = "disease_note")
    private String diseaseNote;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Pharmacy pharmacy;

    @Column(name = "appointment_status")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "appointment")
    private Set<AppointmentMedicine> appointments;

    @Transient
    private List<AppointmentMedicine> appointmentMedicines;

}
