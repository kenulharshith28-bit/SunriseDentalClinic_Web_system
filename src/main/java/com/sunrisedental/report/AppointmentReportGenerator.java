package com.sunrisedental.report;

public class AppointmentReportGenerator
        implements ReportGenerator {

    @Override
    public String generate() {
        return "Appointment Report";
    }
}