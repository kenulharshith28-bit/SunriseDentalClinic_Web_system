package com.sunrisedental.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {

    private int appointmentId;
    private String appointmentNumber;
    private int patientId;
    private int dentistId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;
    private String notes;

    public Appointment(
            final int appointmentId,
            final String appointmentNumber,
            final int patientId,
            final int dentistId,
            final LocalDate appointmentDate,
            final LocalTime appointmentTime,
            final String status,
            final String notes) {

        this.appointmentId = appointmentId;
        this.appointmentNumber = appointmentNumber;
        this.patientId = patientId;
        this.dentistId = dentistId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.notes = notes;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDentistId() {
        return dentistId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public Appointment(
            final int appointmentId,
            final String appointmentNumber) {

        this(
                appointmentId,
                appointmentNumber,
                0,
                0,
                null,
                null,
                null,
                null);
    }
}