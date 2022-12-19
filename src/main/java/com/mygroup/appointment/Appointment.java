package com.mygroup.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mygroup.doctor.Doctor;
import com.mygroup.patient.Patient;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Size;

import java.security.PrivateKey;
import java.time.LocalDate;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer aptID;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;
    @Column(name = "apt_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate aptDate;
    @Size(min = 10, max = 255)
    @Column(name = "patient_comment", length = 255, nullable = false)
    private String comment;



    public Integer getAptID() {
        return aptID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setAptID(Integer aptID) {
        this.aptID = aptID;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getAptDate() {
        return aptDate;
    }

    public void setAptDate(LocalDate aptDate) {
        this.aptDate = aptDate;
    }
}
