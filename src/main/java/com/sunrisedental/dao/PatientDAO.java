package com.sunrisedental.dao;

import com.sunrisedental.model.Patient;

import java.sql.SQLException;
import java.util.List;

public interface PatientDAO {

    List<Patient> findAllPatients()
            throws SQLException;
}