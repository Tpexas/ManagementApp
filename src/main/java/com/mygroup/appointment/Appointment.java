package com.mygroup.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mygroup.AppointmentDetails;
import com.mygroup.doctor.Doctor;
import com.mygroup.patient.Patient;
import javax.persistence.*;
import javax.validation.constraints.Size;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


@Entity
@Table(name = "appointments")
public class Appointment implements AppointmentDetails {
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

    @Override
    public long daysTillAppointment(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        Date date = new Date();
        Date date1 = Date.from(aptDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        sdf.format(date1);
        sdf.format(date);
        long diffInMillies = Math.abs(date1.getTime() - date.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return diff;
    }
    public Appointment() {
    }

    public Appointment(Integer aptID, Patient patient, Doctor doctor, LocalDate aptDate, String comment) {
        this.aptID = aptID;
        this.patient = patient;
        this.doctor = doctor;
        this.aptDate = aptDate;
        this.comment = comment;
    }

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
