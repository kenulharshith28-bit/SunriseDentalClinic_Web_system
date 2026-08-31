package com.sunrisedental.dao;

import com.sunrisedental.model.Dentist;

import java.sql.SQLException;
import java.util.List;

public interface DentistDAO {

    List<Dentist> findAllDentists()
            throws SQLException;

    boolean saveDentist(
            Dentist dentist)
            throws SQLException;

    boolean deleteDentist(int dentistId)
            throws SQLException;
}