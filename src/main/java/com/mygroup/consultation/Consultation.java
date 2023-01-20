package com.mygroup.consultation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mygroup.Gender;
import com.mygroup.doctor.Doctor;
import com.mygroup.patient.Patient;
import javax.persistence.*;

import javax.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "consultations")
public class Consultation{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer consID;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;
    @Column(name = "register_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate regDate;
    @Size(min = 10, max = 255)
    @Column(name = "patient_comment", length = 255, nullable = false)
    private String patientComment;
    @Size(min = 10, max = 255)
    @Column(name = "doctor_comment", length = 255, nullable = true)
    private String doctorComment = "Neatsakyta";
    @Size(min = 2, max = 10)
    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.NEATSAKYTA;

    public Integer getConsID() {
        return consID;
    }

    public void setConsID(Integer consID) {
        this.consID = consID;
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

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public String getPatientComment() {
        return patientComment;
    }

    public void setPatientComment(String patientComment) {
        this.patientComment = patientComment;
    }

    public String getDoctorComment() {
        return doctorComment;
    }

    public void setDoctorComment(String doctorComment) {
        this.doctorComment = doctorComment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "consID=" + consID +
                ", patient=" + patient +
                ", doctor=" + doctor +
                ", regDate=" + regDate +
                ", patientComment='" + patientComment + '\'' +
                ", doctorComment='" + doctorComment + '\'' +
                ", status=" + status +
                '}';
    }
}
