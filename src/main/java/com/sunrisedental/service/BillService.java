package com.sunrisedental.service;

import com.sunrisedental.dao.BillDAO;
import com.sunrisedental.dao.TreatmentDAO;
import com.sunrisedental.dao.TreatmentTypeDAO;

import com.sunrisedental.model.Bill;
import com.sunrisedental.model.Treatment;
import com.sunrisedental.model.TreatmentType;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BillService {

    private final BillDAO billDAO;
    private final TreatmentDAO treatmentDAO;
    private final TreatmentTypeDAO treatmentTypeDAO;
    private final BillCalculator billCalculator;

    public BillService(
            final BillDAO billDAO) {

        this.billDAO = billDAO;
        this.treatmentDAO = null;
        this.treatmentTypeDAO = null;
        this.billCalculator = null;
    }

    public BillService(
            final BillDAO billDAO,
            final TreatmentDAO treatmentDAO,
            final TreatmentTypeDAO treatmentTypeDAO,
            final BillCalculator billCalculator) {

        this.billDAO = billDAO;
        this.treatmentDAO = treatmentDAO;
        this.treatmentTypeDAO = treatmentTypeDAO;
        this.billCalculator = billCalculator;
    }

    public boolean saveBill(
            final Bill bill)
            throws SQLException {

        if (bill == null) {
            throw new IllegalArgumentException(
                    "Bill must not be null");
        }

        try {

            return billDAO.saveBill(
                    bill);

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to save bill",
                    exception);
        }
    }

    public Optional<Bill> findBill(
            final int billId)
            throws SQLException {

        if (billId <= 0) {
            throw new IllegalArgumentException(
                    "Bill ID must be greater than zero");
        }

        try {

            return billDAO.findByBillId(
                    billId);

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to find bill",
                    exception);
        }
    }

    public BigDecimal calculateBillTotal(
            final int appointmentId)
            throws SQLException {

        if (appointmentId <= 0) {
            throw new IllegalArgumentException(
                    "Appointment ID must be greater than zero");
        }

        if (treatmentDAO == null
                || treatmentTypeDAO == null
                || billCalculator == null) {

            throw new IllegalStateException(
                    "Billing calculation dependencies are not configured");
        }

        try {

            final List<Treatment> treatments =
                    treatmentDAO
                            .findByAppointmentId(
                                    appointmentId);

            final List<TreatmentType> treatmentTypes =
                    new ArrayList<>();

            for (Treatment treatment : treatments) {

                final Optional<TreatmentType> treatmentType =
                        treatmentTypeDAO
                                .findById(
                                        treatment.getTreatmentTypeId());

                treatmentType.ifPresent(
                        treatmentTypes::add);
            }

            return billCalculator
                    .calculateTotal(
                            treatmentTypes);

        } catch (SQLException exception) {

            throw new SQLException(
                    "Failed to calculate bill total",
                    exception);
        }
    }
}