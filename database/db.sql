CREATE DATABASE IF NOT EXISTS sunrise_dental_clinic;

USE sunrise_dental_clinic;



-- USER TABLE

CREATE TABLE IF NOT EXISTS users (
                                     user_id INT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL
    );



-- PATIENT TABLE

CREATE TABLE IF NOT EXISTS patients (
                                        patient_id INT AUTO_INCREMENT PRIMARY KEY,
                                        first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    date_of_birth DATE,
    address VARCHAR(255)
    );



-- DENTIST TABLE

CREATE TABLE IF NOT EXISTS dentists (
                                        dentist_id INT AUTO_INCREMENT PRIMARY KEY,
                                        first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    specialization VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100)
    );



-- TREATMENT TYPE TABLE

CREATE TABLE IF NOT EXISTS treatment_types (
                                               treatment_type_id INT AUTO_INCREMENT PRIMARY KEY,
                                               treatment_name VARCHAR(100) NOT NULL,
    treatment_fee DECIMAL(10,2) NOT NULL
    );



-- APPOINTMENT TABLE

CREATE TABLE IF NOT EXISTS appointments (
                                            appointment_id INT AUTO_INCREMENT PRIMARY KEY,
                                            appointment_number VARCHAR(30) NOT NULL UNIQUE,

    patient_id INT NOT NULL,
    dentist_id INT NOT NULL,

    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,

    status VARCHAR(30) NOT NULL DEFAULT 'SCHEDULED',
    notes VARCHAR(255),

    FOREIGN KEY (patient_id)
    REFERENCES patients(patient_id),

    FOREIGN KEY (dentist_id)
    REFERENCES dentists(dentist_id)
    );



-- TREATMENT TABLE

CREATE TABLE IF NOT EXISTS treatments (
                                          treatment_id INT AUTO_INCREMENT PRIMARY KEY,
                                          appointment_id INT NOT NULL,
                                          treabillsbill_idbill_idbill_idtment_type_id INT NOT NULL,

                                          description VARCHAR(255),

    FOREIGN KEY (appointment_id)
    REFERENCES appointments(appointment_id),

    FOREIGN KEY (treatment_type_id)
    REFERENCES treatment_types(treatment_type_id)
    );



-- BILL TABLE

CREATE TABLE IF NOT EXISTS bills (
                                     bill_id INT AUTO_INCREMENT PRIMARY KEY,
                                     appointment_id INT NOT NULL,
                                     total_amount DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (appointment_id)
    REFERENCES appointments(appointment_id)
    );