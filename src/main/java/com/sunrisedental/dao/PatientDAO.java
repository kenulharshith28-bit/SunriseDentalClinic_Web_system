package com.sunrisedental.dao;

import com.sunrisedental.model.Patient;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PatientDAO {

    List<Patient> findAllPatients()
            throws SQLException;

    Optional<Patient> findById(
            int patientId)
            throws SQLException;

    boolean savePatient(
            Patient patient)
            throws SQLException;

    boolean updatePatient(
            Patient patient)
            throws SQLException;

    boolean deletePatient(
            int patientId)
            throws SQLException;

    int getPatientCount()
            throws SQLException;
}