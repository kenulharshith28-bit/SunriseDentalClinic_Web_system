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
        this.treatmentName = treatmentName;
    }

    public int getTreatmentTypeId() {
        return treatmentTypeId;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public BigDecimal getTreatmentFee() {

        // treatment fee has not yet been implemented.
        return BigDecimal.ZERO;
    }
}