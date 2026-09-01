package com.sunrisedental.service;

import com.sunrisedental.model.TreatmentType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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
    void shouldIncludeConsultationFeeInBillTotal() {

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
                List.of(
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
    void shouldIncludeConsultationFeeForSingleTreatment() {

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
    void shouldReturnZeroForEmptyTreatmentList() {

        final BigDecimal result =
                billCalculator
                        .calculateTotal(
                                List.of());

        assertEquals(
                BigDecimal.ZERO,
                result);
    }

    @Test
    void shouldReturnZeroForNullTreatmentList() {

        final BigDecimal result =
                billCalculator
                        .calculateTotal(
                                null);

        assertEquals(
                BigDecimal.ZERO,
                result);
    }
}