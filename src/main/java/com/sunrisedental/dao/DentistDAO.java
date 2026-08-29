package com.sunrisedental.dao;

import com.sunrisedental.model.Dentist;

import java.sql.SQLException;
import java.util.List;

public interface DentistDAO {

    List<Dentist> findAllDentists()
            throws SQLException;
}