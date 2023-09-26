package com.nibm.smartmedicine.entity;

public enum UserRole {
    DOCTOR("DOCTOR"),
    PATIENT("PATIENT"),
    PHARMACY("PHARMACY");

    private final String full;

    UserRole(String full) {
        this.full = full;
    }

    public String getFull() {
        return this.full;
    }
}
