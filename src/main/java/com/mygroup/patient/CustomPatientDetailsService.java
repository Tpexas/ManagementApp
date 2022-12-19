package com.mygroup.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomPatientDetailsService implements UserDetailsService {
    @Autowired
    private PatientRepository repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Patient patient = repo.findPatientsByEmail(email);
        if(patient == null){
            throw new UsernameNotFoundException("Pacientas nerastas");
        }
        return new CustomPatientDetails(patient);
    }
}
