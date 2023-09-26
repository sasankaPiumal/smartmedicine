package com.nibm.smartmedicine.repository.specification;

import com.nibm.smartmedicine.entity.*;
import org.springframework.data.jpa.domain.Specification;

public class AppointmentSpecification {
    private AppointmentSpecification() {
    }

    public static Specification<Appointment> search(String searchValue) {
        if (searchValue == null || searchValue.isEmpty()) {
            return null;
        }
        String likeValue = "%".concat(searchValue.toLowerCase()).concat("%");
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("appointmentId")), likeValue),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("doctor").get("name")), likeValue)
        );
    }

    public static Specification<Appointment> status(AppointmentStatus status) {
        if (status == null) {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("appointmentStatus"), status);
    }

    public static Specification<Appointment> patient(Long id) {
        if (id == null) {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("patient").<Patient>get("user").<User>get("id"), id);
    }

    public static Specification<Appointment> doctor(Long id) {
        if (id == null) {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("doctor").<Doctor>get("user").<User>get("id"), id);
    }

    public static Specification<Appointment> pharmacy(Long id) {
        if (id == null) {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("pharmacy").<Pharmacy>get("user").<User>get("id"), id);
    }
}
