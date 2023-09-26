package com.nibm.smartmedicine.repository.specification;

import com.nibm.smartmedicine.entity.Appointment;
import org.springframework.data.jpa.domain.Specification;

public class DoctorSpecification {
    private DoctorSpecification() {
    }

    public static Specification<Appointment> search(String searchValue) {
        if (searchValue == null || searchValue.isEmpty()) {
            return null;
        }
        String likeValue = "%".concat(searchValue.toLowerCase()).concat("%");
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), likeValue),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), likeValue)
        );
    }

}
