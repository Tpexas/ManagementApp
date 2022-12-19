package com.mygroup.patient;

import com.mygroup.Gender;
import com.mygroup.appointment.Appointment;
import com.mygroup.consultation.Consultation;
import com.mygroup.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer patientID;
    @Size(min = 2, max = 45)
    @Column(length = 45, nullable = false)
    private String name;
    @Size(min = 2, max = 45)
    @Column(length = 45, nullable = false)
    private String surname;
    @Size(min = 8, max = 45)
    @Column(length = 45, nullable = false)
    private String password;
    @NotBlank(message = "Įveskite veikiantį el. paštą")
    @Email(message = "Įveskite veikiantį el. paštą")
    @Column(length = 45, nullable = false, unique = true)
    private String email;
    @Size(min = 4, max = 14)
    @Column(length = 14, nullable = false)
    private String phone;
    @Size(min = 2, max = 45)
    @Column(length = 45, nullable = false)
    private String address;
    @Size(min = 10, max = 11)
    @Column(length = 11, nullable = false, unique = true, name = "personal_code")
    private String personalCode;
    @NotNull(message = "Įveskite amžių")
    @Column(length = 3, nullable = false)
    private Integer age;
    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "patient")
    private List<Consultation> consultations;
    @ManyToMany
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(String personalCode) {
        this.personalCode = personalCode;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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
