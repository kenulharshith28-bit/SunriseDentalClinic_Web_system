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

        if (treatmentTypes == null
                || treatmentTypes.isEmpty()) {

            return BigDecimal.ZERO;
        }

        BigDecimal total =
                BigDecimal.ZERO;

        for (TreatmentType treatmentType
                : treatmentTypes) {

            if (treatmentType != null
                    && treatmentType.getTreatmentFee() != null) {

                total =
                        total.add(
                                treatmentType
                                        .getTreatmentFee());
            }
        }

        return total.add(
                CONSULTATION_FEE);
    }
}