package com.sunrisedental.service;

public interface EmailService {

    void sendEmail(
            String recipient,
            String subject,
            String body);
}