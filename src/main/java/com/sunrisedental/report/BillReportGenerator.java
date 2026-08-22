package com.sunrisedental.report;

public class BillReportGenerator
        implements ReportGenerator {

    @Override
    public String generate() {
        return "Bill Report";
    }
}