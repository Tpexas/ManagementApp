package com.mygroup.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired private DoctorRepository repo;
    public List<Doctor> listAll() {return (List<Doctor>) repo.findAll();}

    public List<Doctor> listAll(String keyword) {
        if (keyword != null){
            return (List<Doctor>) repo.findAll(keyword);
        }
        return (List<Doctor>) repo.findAll();
    }

    public void save(Doctor doctor) {
        repo.save(doctor);
    }

    public Doctor get(Integer doctorID) throws DoctorNotFoundException {
        Optional<Doctor> result = repo.findById(doctorID);
        if(result.isPresent()){
            return result.get();
        }
        throw new DoctorNotFoundException("Nerastas gydytojas su ID = :" + doctorID);
    }

    public void delete(Integer doctorID) throws DoctorNotFoundException {
        Long count = repo.countByDoctorID(doctorID);
        if (count == null || count == 0){
            throw new DoctorNotFoundException("Gydytojas nerastas su Šiuo ID "+ doctorID);
        }
        repo.deleteById(doctorID);
    }
}
