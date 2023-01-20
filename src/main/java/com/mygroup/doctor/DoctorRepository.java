package com.mygroup.doctor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DoctorRepository extends CrudRepository<Doctor, Integer> {
    public Long countByDoctorID(Integer doctorID);

    @Query("SELECT d FROM Doctor d WHERE d.name LIKE %?1%" +
            "OR d.surname LIKE %?1%" +
            "OR d.field LIKE %?1%")
    public List<Doctor> findAll(String keyword);
}
