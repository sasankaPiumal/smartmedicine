package com.nibm.smartmedicine.repository.specification;

import com.nibm.smartmedicine.entity.User;
import com.nibm.smartmedicine.entity.UserRole;
import com.nibm.smartmedicine.entity.UserStatus;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<User> search(String searchValue) {
        if (searchValue == null) {
            return null;
        }
        return (root, cq, cb) -> cb.or(
                cb.like(root.get("name"), "%" + searchValue + "%"),
                cb.like(root.get("email"), "%" + searchValue + "%"),
                cb.like(root.get("mobileNumber"), "%" + searchValue + "%")
        );
    }

    public static Specification<User> isEnable(UserStatus status) {
        boolean enable;
        if (status == null) {
            return null;
        } else enable = status == UserStatus.ENABLE;
        return (root, cq, cb) -> cb.equal(root.get("enabled"), enable);
    }

    public static Specification<User> role(UserRole role) {
        if (role == null) {
            return null;
        }

        return (root, cq, cb) -> cb.equal(root.get("role"), role);

    }
}
