package com.sunrisedental.util;

import com.sunrisedental.dao.BillDAO;
import com.sunrisedental.dao.BillDAOImpl;
import com.sunrisedental.model.Bill;

import java.util.Optional;

public class BillDAOConnectionCheck {

    public static void main(String[] args)
            throws Exception {

        final BillDAO billDAO =
                new BillDAOImpl();

        final Optional<Bill> bill =
                billDAO.findByBillId(3);

        if (bill.isPresent()) {

            System.out.println(
                    "Bill found: "
                            + bill.get().getBillId());

            System.out.println(
                    "Appointment ID: "
                            + bill.get().getAppointmentId());

            System.out.println(
                    "Total amount: "
                            + bill.get().getTotalAmount());

        } else {

            System.out.println(
                    "Bill not found");
        }
    }
}