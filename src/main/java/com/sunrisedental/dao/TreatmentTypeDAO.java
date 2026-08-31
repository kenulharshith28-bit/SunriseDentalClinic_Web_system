package com.sunrisedental.dao;

import com.sunrisedental.model.TreatmentType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TreatmentTypeDAO {

    List<TreatmentType> findAllTreatmentTypes()
            throws SQLException;

    Optional<TreatmentType> findById(
            int treatmentTypeId)
            throws SQLException;

    boolean saveTreatmentType(
            TreatmentType treatmentType)
            throws SQLException;

    boolean deleteTreatmentType(
            int treatmentTypeId)
            throws SQLException;
}