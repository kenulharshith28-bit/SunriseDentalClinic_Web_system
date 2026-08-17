package com.sunrisedental.model;

import java.math.BigDecimal;

/**
 * Represents a treatment type provided by Sunrise Dental Clinic.
 */
public class TreatmentType {

    private int treatmentTypeId;
    private String treatmentName;
    private BigDecimal treatmentFee;

    public TreatmentType(
            final int treatmentTypeId,
            final String treatmentName,
            final BigDecimal treatmentFee) {

        this.treatmentTypeId = treatmentTypeId;
    }

    public int getTreatmentTypeId() {
        return treatmentTypeId;
    }

    public String getTreatmentName() {

        //  treatment name has not yet been implemented.
        return null;
    }
}