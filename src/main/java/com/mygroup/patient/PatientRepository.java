package com.mygroup.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer>, PagingAndSortingRepository<Patient, Integer> {
    public Long countByPatientID(Integer patientID);

    @Query("SELECT p FROM Patient p WHERE p.email = ?1")
    Patient findPatientsByEmail(String email);

    @Query("select p from Patient p WHERE " +
            "concat(p.patientID, p.name, p.surname, p.email, p.phone, p.address," +
            "p.personalCode, p.age, p.gender, p.roles) like %?1%")
    public Page<Patient> findAll(String keyword, Pageable pageable);

    @Query(value = "SELECT avg(p.age) FROM Patient p")
    public Double avg();

    @Query(value = "SELECT min(p.age) FROM Patient p")
    public int min();

    @Query(value = "SELECT max (p.age) FROM Patient p")
    public int max();

}
