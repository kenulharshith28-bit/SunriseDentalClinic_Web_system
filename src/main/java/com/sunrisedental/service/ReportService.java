package com.sunrisedental.service;

import com.sunrisedental.report.ReportGenerator;
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

        final ReportGenerator generator =
                reportGeneratorFactory.create(
                        reportType);

        if (generator == null) {
            throw new IllegalArgumentException(
                    "Unsupported report type");
        }

        return generator.generate();
    }
}