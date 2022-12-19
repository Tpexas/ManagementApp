package com.mygroup.appointment;

import com.mygroup.doctor.Doctor;
import com.mygroup.doctor.DoctorNotFoundException;
import com.mygroup.doctor.DoctorRepository;
import com.mygroup.patient.Patient;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository repo;

    public List<Appointment> listAll(){
        return (List<Appointment>) repo.findAll();
    }

    public void save(Appointment appointment) {
        repo.save(appointment);
    }

    public Appointment get(Integer aptID) throws AppointmentNotFoundException{
        Optional<Appointment> result = repo.findById(aptID);
        if (result.isPresent()) {
            return result.get();
        }
        throw new AppointmentNotFoundException("Nerastas apsilankymas su ID = :" + aptID);
    }

    public void delete(Integer aptID) throws AppointmentNotFoundException{
        Long count = repo.countByAptID(aptID);
        if (count == null || count ==0) {
            throw new AppointmentNotFoundException("Nerastas apsilankymas su ID = :" + aptID);
        }
        repo.deleteById(aptID);
    }

}
