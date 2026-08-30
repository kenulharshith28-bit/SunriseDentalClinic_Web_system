package com.sunrisedental.dao;

import com.sunrisedental.model.Treatment;

import java.sql.SQLException;
import java.util.List;

public interface TreatmentDAO {

    boolean saveTreatment(
            Treatment treatment)
            throws SQLException;

    List<Treatment> findByAppointmentId(
            int appointmentId)
            throws SQLException;
}