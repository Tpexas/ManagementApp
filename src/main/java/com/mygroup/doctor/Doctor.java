package com.mygroup.doctor;

import com.mygroup.appointment.Appointment;
import com.mygroup.consultation.Consultation;
import com.mygroup.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer doctorID;
    @Size(min = 2, max = 45)
    @Column(length = 45, nullable = false)
    private String name;
    @Size(min = 2, max = 45)
    @Column(length = 45, nullable = false)
    private String surname;
    @Size(min = 2, max = 45)
    @Column(length = 45, nullable = false)
    private String field;
    @NotBlank(message = "Įveskite veikiantį el. paštą")
    @Email(message = "Įveskite veikiantį el. paštą")
    @Column(length = 45, nullable = false, unique = true)
    private String email;
    @Size(min = 8, max = 45)
    @Column(length = 45, nullable = false)
    private String password;
    @Size(min = 4, max = 14)
    @Column(length = 14, nullable = false)
    private String phone;
    @Size(min = 2, max = 45)
    @Column(length = 45, nullable = false)
    private String address;
    @Size(min = 10, max = 11)
    @Column(length = 11,nullable = false, unique = true, name = "personal_code")
    private String personalCode;
    @Column(length = 3, nullable = false)
    private Integer age;
    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "doctor")
    private List<Consultation> consultations;

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Integer getDoctorID() {
        return doctorID;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public void setDoctorID(Integer doctorID) {
        this.doctorID = doctorID;
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

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorID=" + doctorID +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", field='" + field + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
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
