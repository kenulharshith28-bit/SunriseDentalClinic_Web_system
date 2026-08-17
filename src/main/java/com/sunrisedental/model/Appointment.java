package com.sunrisedental.model;

/**
 * Represents an appointment registered in Sunrise Dental Clinic.
 */
public class Appointment {

    private int appointmentId;
    private String appointmentNumber;

    public Appointment(
            final int appointmentId,
            final String appointmentNumber) {

        this.appointmentId = appointmentId;
        this.appointmentNumber = appointmentNumber;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }
}