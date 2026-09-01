package com.sunrisedental.service;

import com.sunrisedental.model.TreatmentType;

import java.math.BigDecimal;
import java.util.List;

public interface BillCalculator {

    BigDecimal calculateTotal(
            List<TreatmentType> treatmentTypes);

    BigDecimal getConsultationFee();
}