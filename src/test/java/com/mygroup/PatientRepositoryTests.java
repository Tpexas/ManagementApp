package com.mygroup;

import com.mygroup.patient.Gender;
import com.mygroup.patient.Patient;
import com.mygroup.patient.PatientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class PatientRepositoryTests {
    @Autowired private PatientRepository repo;

    @Test
    public void testAddNew(){
        Patient patient = new Patient();
        patient.setAddress("asd");
        patient.setAge(10);
        patient.setName("jonas");
        patient.setSurname("bakalas");
        patient.setGender(Gender.VYRAS);
        patient.setEmail("cen@");
        patient.setPhone("85853242");
        patient.setPersonalCode("938123431");
        patient.setPassword("jonas13");

        Patient savedPatient = repo.save(patient);

        Assertions.assertThat(savedPatient).isNotNull();
        Assertions.assertThat(savedPatient.getPatientID()).isGreaterThan(0);
        }

    @Test
    public void testListAll(){
    Iterable<Patient> patients = repo.findAll();
        Assertions.assertThat(patients).hasSizeGreaterThan(0);

        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }

    @Test
    public void testUpdate(){
        Integer patientId = 1;
        Optional<Patient> optionalPatient = repo.findById(patientId);
        Patient patient = optionalPatient.get();
        patient.setPassword("naujaspass");
        repo.save(patient);

        Patient updatedPatient = repo.findById(patientId).get();
        Assertions.assertThat(updatedPatient.getPassword()).isEqualTo("naujaspass");
    }

    @Test
    public void testGet(){
        Integer patientId = 9;
        Optional<Patient> optionalPatient = repo.findById(patientId);
        Patient patient = optionalPatient.get();

        Assertions.assertThat(optionalPatient).isPresent();
        System.out.println(optionalPatient.get());
        System.out.println("Gender: " + patient.getGender());
    }

    @Test
    public void testDelete(){
        Integer patientId = 2;
        repo.deleteById(patientId);

        Optional<Patient> optionalPatient = repo.findById(patientId);
        Assertions.assertThat(optionalPatient).isNotPresent();
    }

}
