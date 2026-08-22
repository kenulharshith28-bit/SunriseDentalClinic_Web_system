package com.sunrisedental.service;

import com.sunrisedental.report.ReportGeneratorFactory;

public class ReportService {

    private final ReportGeneratorFactory reportGeneratorFactory;

    public ReportService(
            final ReportGeneratorFactory reportGeneratorFactory) {

        this.reportGeneratorFactory =
                reportGeneratorFactory;
    }

    public String generateReport(
            final String reportType) {

        // Report generation is not implemented.
        return null;
    }
}