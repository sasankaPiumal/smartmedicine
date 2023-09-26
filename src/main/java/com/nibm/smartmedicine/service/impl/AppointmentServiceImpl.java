package com.nibm.smartmedicine.service.impl;

import com.nibm.smartmedicine.dto.request.appointment.AddMedicineRequest;
import com.nibm.smartmedicine.dto.request.appointment.AppointmentMedicineRequest;
import com.nibm.smartmedicine.entity.*;
import com.nibm.smartmedicine.repository.AppointmentMedicineRepository;
import com.nibm.smartmedicine.repository.AppointmentRepository;
import com.nibm.smartmedicine.repository.specification.AppointmentSpecification;
import com.nibm.smartmedicine.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final DoctorService doctorService;

    private final PatientService patientService;

    private final EmailService emailService;

    private final AppointmentRepository repository;

    private final AppointmentMedicineRepository appointmentMedicineRepository;

    private final PharmacyService pharmacyService;

    public AppointmentServiceImpl(DoctorService doctorService, PatientService patientService, EmailService emailService, AppointmentRepository repository, AppointmentMedicineRepository appointmentMedicineRepository, PharmacyService pharmacyService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.emailService = emailService;
        this.repository = repository;
        this.appointmentMedicineRepository = appointmentMedicineRepository;
        this.pharmacyService = pharmacyService;
    }

    @Override
    public Appointment save(Appointment appointment) {

        Optional<Doctor> doctor = doctorService.findById(appointment.getDoctor().getId());

        Optional<Patient> patient = patientService.getByUserId(appointment.getPatient().getId());

        appointment.setPatient(patient.get());

        if(doctor.isPresent())
            emailService.sendSimpleMessage(doctor.get().getEmail(), "[SMART MEDICINE] New Patient Appointment - " + appointment.getAppointmentId(), getNewAppointmentMailBody(appointment));

        return repository.save(appointment);
    }

    @Override
    public Appointment getLastRecord() {
        return repository.findTopByOrderByIdDesc();
    }

    @Override
    public Page<Appointment> getPage(PageRequest pageRequest, String searchKey, AppointmentStatus appointmentStatus,
                                     Long doctorId, Long patientId, Long pharmacyId) {

        Specification<Appointment> specification = Specification.where(
                 AppointmentSpecification.status(appointmentStatus))
                .and(AppointmentSpecification.search(searchKey == null ? null : searchKey.trim()))
                .and(AppointmentSpecification.patient(patientId)
                .and(AppointmentSpecification.doctor(doctorId)
                .and(AppointmentSpecification.pharmacy(pharmacyId))));

        return repository.findAll(specification, pageRequest);
    }

    @Override
    public Optional<Appointment> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void updateMedicines(AddMedicineRequest request) {

        Optional<Appointment> appointment = repository.findById(request.getAppointmentId());

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(request.getPharmacyId());

        if(appointment.isPresent()) {
            appointment.get().setPharmacy(pharmacy);
            appointment.get().setAppointmentStatus(AppointmentStatus.CHECKED);

            repository.save(appointment.get());

            List<AppointmentMedicine> appointmentMedicineList = request.getMedicineRequests().stream().map(AppointmentMedicineRequest::getAppointmentMedicine).collect(Collectors.toList());

            appointmentMedicineList.forEach(a -> a.setAppointment(appointment.get()));

            appointmentMedicineRepository.saveAll(appointmentMedicineList);

            Optional<Appointment> appointment1 = repository.findById(appointment.get().getId());

            Optional<Patient> patient = patientService.getById(appointment1.get().getPatient().getId());

            if (patient.isPresent())
                emailService.sendSimpleMessage(patient.get().getEmail(), "[SMART MEDICINE] Prescription Released - " + appointment.get().getAppointmentId(), getPreScriptionReleasedMailBody(appointment.get()));
        }

    }

    @Override
    public void markAsDone(AddMedicineRequest request) {

        Optional<Appointment> appointment = repository.findById(request.getAppointmentId());

        if(appointment.isPresent()) {
            appointment.get().setAppointmentStatus(AppointmentStatus.DONE);

            repository.save(appointment.get());

            List<AppointmentMedicine> appointmentMedicineList = appointmentMedicineRepository.findAllByAppointmentId(appointment.get().getId());

            Optional<Patient> patient = patientService.getById(appointment.get().getPatient().getId());

            patient.ifPresent(value -> emailService.sendSimpleMessage(value.getEmail(), "[SMART MEDICINE] Prescription is ready - " + appointment.get().getAppointmentId(), getPrescriptionReadyMailBody(appointment.get(), appointmentMedicineList)));
        }
    }

    private String getPreScriptionReleasedMailBody(Appointment appointment) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Appointment ID: ");
        stringBuilder.append(appointment.getAppointmentId()).append("\n\n");
        stringBuilder.append("Doctor: ");
        stringBuilder.append(doctorService.findById(appointment.getDoctor().getId()).get().getName()).append("\n\n");
        stringBuilder.append("Contact: ");
        stringBuilder.append(patientService.getById(appointment.getDoctor().getId()).get().getMobileNumber()).append("\n\n");
        stringBuilder.append("Your prescription is ready.").append("\n\n");
        return stringBuilder.toString();
    }

    private String getPrescriptionReadyMailBody(Appointment appointment, List<AppointmentMedicine> medicines) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Appointment ID: ");
        stringBuilder.append(appointment.getAppointmentId()).append("\n\n");
        stringBuilder.append("Medicines: ");

        medicines.forEach( m -> {
            stringBuilder.append("\n").append(m.getMedicine().getName());
        });

        stringBuilder.append("\n\nTotal: ");
        stringBuilder.append("Rs. " + medicines.stream().map(m ->  m.getMedicine().getPrice()).mapToDouble(Double::doubleValue).sum());

        stringBuilder.append("\n\nPharmacy: ");
        stringBuilder.append(pharmacyService.findById(appointment.getPharmacy().getId()).get().getName()).append("\n\n");
        stringBuilder.append("Contact: ");
        stringBuilder.append(pharmacyService.findById(appointment.getPharmacy().getId()).get().getContactNumber()).append("\n\n");

        stringBuilder.append("Your medicines are ready.").append("\n\n");
        return stringBuilder.toString();
    }

    private String getNewAppointmentMailBody(Appointment appointment) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Appointment ID: ");
        stringBuilder.append(appointment.getAppointmentId()).append("\n\n");
        stringBuilder.append("Patient: ");
        stringBuilder.append(patientService.getById(appointment.getPatient().getId()).get().getName()).append("\n\n");
        stringBuilder.append("Contact: ");
        stringBuilder.append(patientService.getById(appointment.getPatient().getId()).get().getMobileNumber()).append("\n\n");
        stringBuilder.append("Disease: ");
        stringBuilder.append(appointment.getDiseaseNote());
        return stringBuilder.toString();
    }
}
