package com.mygroup.patient;

import com.mygroup.role.Role;
import com.mygroup.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired private PatientRepository patientRepo;

    public List<Patient> listAll(){
        return (List<Patient>) patientRepo.findAll();
    }

    public Page<Patient> listPage(int pageNum, String sortField, String sortDir){
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum-1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        return patientRepo.findAll(pageable);
    }
    public void save(Patient patient) {
        patientRepo.save(patient);
    }

    public Patient get(Integer patientID) throws PatientNotFoundException {
        Optional<Patient> result = patientRepo.findById(patientID);
        if(result.isPresent()){
            return result.get();
        }
        throw new PatientNotFoundException("Nerastas pacientas su ID = :" + patientID);
    }

    public void delete(Integer patientID) throws PatientNotFoundException {
        Long count = patientRepo.countByPatientID(patientID);
        if (count == null || count == 0){
            throw new PatientNotFoundException("Pacientas nerastas su Šiuo ID "+ patientID);
        }
        patientRepo.deleteById(patientID);
    }

    @Autowired
    private RoleRepository roleRepo;
    public void savePatientWithDefaultRole(Patient patient){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(patient.getPassword());
            patient.setPassword(encodedPassword);
            Role rolePatient = roleRepo.findByName("Patient");
            patient.addRole(rolePatient);
            patientRepo.save(patient);
    }

    public List<Role> getRoles(){
        return roleRepo.findAll();
    }

    public Double avg(){
        return patientRepo.avg();
    }

    public int min(){
        return patientRepo.min();
    }

    public int max(){
        return patientRepo.max();
    }
}
