package com.mygroup.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    public Long countByPatientID(Integer patientID);

    @Query("SELECT p FROM Patient p WHERE p.email = ?1")
    Patient findPatientsByEmail(String email);
}
