package com.mygroup.doctor;

import com.mygroup.Employee;
import com.mygroup.appointment.Appointment;
import com.mygroup.consultation.Consultation;
import com.mygroup.user.User;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.List;

import static java.lang.Math.round;

@Entity
@Table(name = "doctors")
public class Doctor extends User implements Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer doctorID;
    @Size(min = 2, max = 45)
    @Column(length = 45, nullable = false)
    private String field;
    @Size(min = 8, max = 45)
    @Column(length = 45, nullable = false)
    private String password;
    @Column(length = 3, nullable = false)
    private Integer age;
    @Column(length = 255, nullable = true)
    @Value("NULL")
    private String picture;
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "doctor")
    private List<Consultation> consultations;
    @Column(length = 8)
    private double salary;

    @Override
    public double salaryAfterTax() {
    float salaryTax = 0.31F;
    double salaryAfterTax = salary-salary*salaryTax;
        return round(salaryAfterTax);
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Integer getDoctorID() {
        return doctorID;
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

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Transient
    public String getImagePath() {
        if (picture == null || doctorID == null) return null;

        return "/doctor-photos/" + doctorID + "/" + picture;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
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
