package com.mygroup.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mygroup.doctor.Doctor;
import com.mygroup.patient.Patient;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.security.PrivateKey;
import java.time.LocalDate;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aptID;
    @ManyToOne
    @JoinColumn(name = "patientID")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "employeeID")
    private Doctor doctor;
    private Integer cabinet;
    @Column(name = "apt_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate aptDate;

}
