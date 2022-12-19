package com.mygroup;

import com.mygroup.patient.Patient;
import com.mygroup.patient.PatientRepository;
import com.mygroup.role.Role;
import com.mygroup.role.RoleRepository;
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
    @Autowired private PatientRepository patientRepo;

    @Autowired private RoleRepository roleRepo;

    @Test
    public void testAddNew(){
        Patient patient = new Patient();
        patient.setAddress("asd");
        patient.setAge(10);
        patient.setName("jonas");
        patient.setSurname("bakalas");
        patient.setGender(Gender.VYRAS);
        patient.setEmail("cen@asd");
        patient.setPhone("85853242");
        patient.setPersonalCode("9381234311");
        patient.setPassword("jonas13aaaassdd");

        Patient savedPatient = patientRepo.save(patient);

        Assertions.assertThat(savedPatient).isNotNull();
        Assertions.assertThat(savedPatient.getPatientID()).isGreaterThan(0);
        }

    @Test
    public void testListAll(){
    Iterable<Patient> patients = patientRepo.findAll();
        Assertions.assertThat(patients).hasSizeGreaterThan(0);

        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }

    @Test
    public void testUpdate(){
        Integer patientId = 1;
        Optional<Patient> optionalPatient = patientRepo.findById(patientId);
        Patient patient = optionalPatient.get();
        patient.setPassword("naujaspass");
        patientRepo.save(patient);

        Patient updatedPatient = patientRepo.findById(patientId).get();
        Assertions.assertThat(updatedPatient.getPassword()).isEqualTo("naujaspass");
    }

    @Test
    public void testGet(){
        Integer patientId = 9;
        Optional<Patient> optionalPatient = patientRepo.findById(patientId);
        Patient patient = optionalPatient.get();

        Assertions.assertThat(optionalPatient).isPresent();
        System.out.println(optionalPatient.get());
        System.out.println("Gender: " + patient.getGender());
    }

    @Test
    public void testDelete(){
        Integer patientId = 2;
        patientRepo.deleteById(patientId);

        Optional<Patient> optionalPatient = patientRepo.findById(patientId);
        Assertions.assertThat(optionalPatient).isNotPresent();
    }

    @Test
    public void testAddRoleToNewPatient(){
        Patient patient = new Patient();
        patient.setAddress("asd");
        patient.setAge(10);
        patient.setName("jonas");
        patient.setSurname("bakalas");
        patient.setGender(Gender.VYRAS);
        patient.setEmail("cen@asssd");
        patient.setPhone("85853242");
        patient.setPersonalCode("9381234312");
        patient.setPassword("jonas13aaaassdd");

        Role rolePatient = roleRepo.findByName("Patient");
        patient.addRole(rolePatient);

        Patient savedPatient = patientRepo.save(patient);

        Assertions.assertThat(savedPatient.getRoles().size()).isEqualTo(1);
    }

    @Test
    public void testAddRoleToExistingPatient(){
        Patient patient = patientRepo.findById(1).get();

        Role rolePatient = roleRepo.findByName("Patient");
        patient.addRole(rolePatient);

        Role roleAdmin = new Role(2L);
        patient.addRole(roleAdmin);

        Patient savedPatient = patientRepo.save(patient);

        Assertions.assertThat(savedPatient.getRoles().size()).isEqualTo(2);

    }

    @Test
    public void testFindPatientByEmail(){
        String email = "pastas@gmaila.com";

        Patient patient = patientRepo.findPatientsByEmail(email);

        Assertions.assertThat(patient).isNotNull();
    }
}
