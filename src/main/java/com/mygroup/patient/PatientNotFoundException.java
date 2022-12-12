package com.mygroup.patient;

public class PatientNotFoundException extends Throwable {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
