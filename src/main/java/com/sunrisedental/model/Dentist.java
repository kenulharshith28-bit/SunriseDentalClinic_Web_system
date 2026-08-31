package com.sunrisedental.model;

public class Dentist {

    private final int dentistId;
    private final String firstName;
    private final String lastName;
    private final String specialization;
    private final String phone;
    private final String email;

    public Dentist(
            final int dentistId,
            final String firstName,
            final String lastName,
            final String specialization,
            final String phone,
            final String email) {

        this.dentistId = dentistId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.phone = phone;
        this.email = email;
    }

    public Dentist(
            final int dentistId,
            final String firstName,
            final String lastName) {

        this(
                dentistId,
                firstName,
                lastName,
                null,
                null,
                null
        );
    }

    public int getDentistId() {
        return dentistId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}