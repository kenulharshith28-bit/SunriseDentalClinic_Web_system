package com.sunrisedental.model;

import java.time.LocalDate;

public class Patient {

    private final int patientId;
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final String email;
    private final LocalDate dateOfBirth;
    private final String address;

    public Patient(
            final int patientId,
            final String firstName,
            final String lastName,
            final String phone,
            final String email,
            final LocalDate dateOfBirth,
            final String address) {

        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    /*
     * Compatibility constructor for older code and tests.
     */
    public Patient(
            final int patientId,
            final String firstName,
            final String lastName,
            final String phone) {

        this(
                patientId,
                firstName,
                lastName,
                phone,
                null,
                null,
                null
        );
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

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}