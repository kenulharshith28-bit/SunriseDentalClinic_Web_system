package com.sunrisedental.model;

import java.math.BigDecimal;

/**
 * Represents a bill generated for a dental appointment.
 */
public class Bill {

    private final int billId;
    private final int appointmentId;
    private final BigDecimal totalAmount;

    public Bill(
            final int billId,
            final int appointmentId,
            final BigDecimal totalAmount) {

        this.billId = billId;
        this.appointmentId = appointmentId;
        this.totalAmount = totalAmount;
    }

    public int getBillId() {
        return billId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}