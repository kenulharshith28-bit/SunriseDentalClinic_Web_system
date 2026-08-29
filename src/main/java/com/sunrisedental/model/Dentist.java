package com.sunrisedental.model;

public class Dentist {

    private final int dentistId;
    private final String firstName;
    private final String lastName;

    public Dentist(
            final int dentistId,
            final String firstName,
            final String lastName) {

        this.dentistId = dentistId;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getFullName() {
        return firstName + " " + lastName;
    }
}