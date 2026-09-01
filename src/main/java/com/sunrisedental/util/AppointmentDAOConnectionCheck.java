package com.sunrisedental.util;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.dao.AppointmentDAOImpl;
import com.sunrisedental.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDAOConnectionCheck {

    public static void main(String[] args)
            throws Exception {

        final AppointmentDAO appointmentDAO =
                new AppointmentDAOImpl();

        final Appointment appointment =
                new Appointment(
                        0,
                        "A-002",
                        1,
                        1,
                        LocalDate.of(
                                2026,
                                8,
                                29),
                        LocalTime.of(
                                11,
                                30),
                        "SCHEDULED",
                        "Follow-up appointment"
                );

        final int appointmentId =
                appointmentDAO
                        .saveAppointment(
                                appointment);

        System.out.println(
                "Appointment saved successfully. ID: "
                        + appointmentId);
    }
}