package com.sunrisedental.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for TreatmentType.
 */
class TreatmentTypeTest {

    @Test
    void shouldReturnCorrectTreatmentTypeId() {

        // ARRANGE
        final TreatmentType treatmentType =
                new TreatmentType(
                        1,
                        "Cleaning",
                        new BigDecimal("3500.00")
                );

        // ACT
        final int result =
                treatmentType.getTreatmentTypeId();

        // ASSERT
        assertEquals(
                1,
                result
        );
    }

    @Test
    void shouldReturnCorrectTreatmentName() {

        // ARRANGE
        final TreatmentType treatmentType =
                new TreatmentType(
                        1,
                        "Cleaning",
                        new BigDecimal("3500.00")
                );

        // ACT
        final String result =
                treatmentType.getTreatmentName();

        // ASSERT
        assertEquals(
                "Cleaning",
                result
        );
    }
}