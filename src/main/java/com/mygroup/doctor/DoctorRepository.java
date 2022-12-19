package com.mygroup.doctor;

import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Integer> {
    public Long countByDoctorID(Integer doctorID);
}
