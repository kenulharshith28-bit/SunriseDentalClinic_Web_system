CREATE DATABASE IF NOT EXISTS sunrise_dental_clinic;

USE sunrise_dental_clinic;



-- PATIENT TABLE
CREATE TABLE patients (
                          patient_id INT AUTO_INCREMENT PRIMARY KEY,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          phone VARCHAR(20),
                          email VARCHAR(100),
                          date_of_birth DATE,
                          address VARCHAR(255)
);



-- DENTIST TABLE
CREATE TABLE dentists (
                          dentist_id INT AUTO_INCREMENT PRIMARY KEY,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          specialization VARCHAR(100),
                          phone VARCHAR(20),
                          email VARCHAR(100)
);



-- APPOINTMENT TABLE
CREATE TABLE appointments (
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

CREATE TABLE treatments (
                            treatment_id INT AUTO_INCREMENT PRIMARY KEY,
                            appointment_id INT NOT NULL,

                            treatment_name VARCHAR(100) NOT NULL,
                            description VARCHAR(255),
                            cost DECIMAL(10,2) NOT NULL,

                            FOREIGN KEY (appointment_id)
                                REFERENCES appointments(appointment_id)
);