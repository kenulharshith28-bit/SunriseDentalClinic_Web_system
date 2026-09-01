package com.sunrisedental.service;

import com.sunrisedental.model.TreatmentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StandardBillCalculatorTest {

    private StandardBillCalculator billCalculator;

    @BeforeEach
    void setUp() {

        billCalculator =
                new StandardBillCalculator();
    }

    @Test
    void shouldCalculateTreatmentFeesAndConsultationFee() {

        final TreatmentType rootCanal =
                new TreatmentType(
                        1,
                        "Root Canal",
                        new BigDecimal("15000.00")
                );

        final TreatmentType cleaning =
                new TreatmentType(
                        2,
                        "Cleaning",
                        new BigDecimal("5000.00")
                );

        final List<TreatmentType> treatmentTypes =
                Arrays.asList(
                        rootCanal,
                        cleaning);

        final BigDecimal result =
                billCalculator
                        .calculateTotal(
                                treatmentTypes);

        assertEquals(
                new BigDecimal("22000.00"),
                result);
    }

    @Test
    void shouldAddConsultationFeeForSingleTreatment() {

        final TreatmentType filling =
                new TreatmentType(
                        1,
                        "Filling",
                        new BigDecimal("4000.00")
                );

        final BigDecimal result =
                billCalculator
                        .calculateTotal(
                                List.of(filling));

        assertEquals(
                new BigDecimal("6000.00"),
                result);
    }

    @Test
    void shouldReturnZeroWhenTreatmentListIsEmpty() {

        final BigDecimal result =
                billCalculator
                        .calculateTotal(
                                Collections.emptyList());

        assertEquals(
                BigDecimal.ZERO,
                result);
    }

    @Test
    void shouldReturnZeroWhenTreatmentListIsNull() {

        final BigDecimal result =
                billCalculator
                        .calculateTotal(
                                null);

        assertEquals(
                BigDecimal.ZERO,
                result);
    }

    @Test
    void shouldReturnConsultationFee() {

        assertEquals(
                new BigDecimal("2000.00"),
                billCalculator
                        .getConsultationFee());
    }
}