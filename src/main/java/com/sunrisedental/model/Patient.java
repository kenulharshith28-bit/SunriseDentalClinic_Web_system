package com.sunrisedental.model;

public class Patient {

    private final int patientId;
    private final String firstName;
    private final String lastName;
    private final String phone;

    public Patient(
            final int patientId,
            final String firstName,
            final String lastName,
            final String phone) {

        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}