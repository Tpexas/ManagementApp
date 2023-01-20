package com.mygroup.patient;

import com.mygroup.Gender;
import com.mygroup.appointment.Appointment;
import com.mygroup.consultation.Consultation;
import com.mygroup.role.Role;
import com.mygroup.user.User;
import org.springframework.data.jpa.repository.Query;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer patientID;
    @Size(min = 8, max = 60)
    @Column(length = 60, nullable = false)
    private String password;
    @NotNull(message = "Įveskite amžių")
    @Column(length = 3, nullable = false)
    private int age;
    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "patient")
    private List<Consultation> consultations;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))


    private Set<Role> roles = new HashSet<>();

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role){
        this.roles.add(role);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientID=" + patientID +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", personalCode='" + personalCode + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", appointments=" + appointments +
                ", consultations=" + consultations +
                '}';
    }

}
