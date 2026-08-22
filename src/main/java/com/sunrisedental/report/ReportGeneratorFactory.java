package com.sunrisedental.report;

public class ReportGeneratorFactory {

    public ReportGenerator create(
            final String reportType) {

        if ("appointment".equalsIgnoreCase(reportType)) {
            return new AppointmentReportGenerator();
        }

        return null;
    }
}