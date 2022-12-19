package com.mygroup.doctor;

public class DoctorNotFoundException extends Throwable{
    public DoctorNotFoundException(String message) {
        super(message);
    }
}
