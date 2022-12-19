package com.mygroup.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    public Long countByAptID(Integer aptID);
}
