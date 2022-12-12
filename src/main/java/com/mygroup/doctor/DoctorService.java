package com.mygroup.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired private DoctorRepository repo;
    public List<Doctor> listAll(){
        return (List<Doctor>) repo.findAll();
    }
}
