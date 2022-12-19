package com.mygroup.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired private PatientRepository repo;

    public List<Patient> listAll(){
        return (List<Patient>) repo.findAll();
    }

    public void save(Patient patient) {
        repo.save(patient);
    }

    public Patient get(Integer patientID) throws PatientNotFoundException {
        Optional<Patient> result = repo.findById(patientID);
        if(result.isPresent()){
            return result.get();
        }
        throw new PatientNotFoundException("Nerastas pacientas su ID = :" + patientID);
    }

    public void delete(Integer patientID) throws PatientNotFoundException {
        Long count = repo.countByPatientID(patientID);
        if (count == null || count == 0){
            throw new PatientNotFoundException("Pacientas nerastas su Šiuo ID "+ patientID);
        }
        repo.deleteById(patientID);
    }
}
