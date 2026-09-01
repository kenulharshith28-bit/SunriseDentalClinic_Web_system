package com.sunrisedental.service;

import com.sunrisedental.model.TreatmentType;

import java.math.BigDecimal;
import java.util.List;

public class StandardBillCalculator
        implements BillCalculator {

    private static final BigDecimal CONSULTATION_FEE =
            new BigDecimal("2000.00");

    @Override
    public BigDecimal calculateTotal(
            final List<TreatmentType> treatmentTypes) {

        /*
         * No treatment means no bill should be generated.
         *
         * This keeps the existing Billing validation working.
         */
        if (treatmentTypes == null
                || treatmentTypes.isEmpty()) {

            return BigDecimal.ZERO;
        }

        BigDecimal treatmentTotal =
                BigDecimal.ZERO;

        boolean hasValidTreatment =
                false;

        for (TreatmentType treatmentType
                : treatmentTypes) {

            if (treatmentType != null
                    && treatmentType.getTreatmentFee() != null) {

                treatmentTotal =
                        treatmentTotal.add(
                                treatmentType
                                        .getTreatmentFee());

                hasValidTreatment =
                        true;
            }
        }

        /*
         * If the list contained only null/invalid treatments,
         * don't charge the consultation fee.
         */
        if (!hasValidTreatment) {

            return BigDecimal.ZERO;
        }

        return treatmentTotal.add(
                CONSULTATION_FEE);
    }

    @Override
    public BigDecimal getConsultationFee() {

        return CONSULTATION_FEE;
    }
}