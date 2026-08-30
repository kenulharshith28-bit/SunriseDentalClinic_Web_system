package com.sunrisedental.model;

public class Treatment {

    private final int treatmentId;
    private final int appointmentId;
    private final int treatmentTypeId;
    private final String description;

    public Treatment(
            final int treatmentId,
            final int appointmentId,
            final int treatmentTypeId,
            final String description) {

        this.treatmentId = treatmentId;
        this.appointmentId = appointmentId;
        this.treatmentTypeId = treatmentTypeId;
        this.description = description;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getTreatmentTypeId() {
        return treatmentTypeId;
    }

    public String getDescription() {
        return description;
    }
}