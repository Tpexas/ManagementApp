package com.mygroup.user;

import com.mygroup.Gender;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
public class User {
    @Size(min = 2, max = 45)
    @Column(length = 45, nullable = false)
    protected String name;
    @Size(min = 2, max = 45)
    @Column(length = 45, nullable = false)
    protected String surname;
    @NotBlank(message = "Įveskite veikiantį el. paštą")
    @Email(message = "Įveskite veikiantį el. paštą")
    @Column(length = 45, nullable = false, unique = true)
    protected String email;
    @Size(min = 4, max = 14)
    @Column(length = 14, nullable = false)
    protected String phone;
    @Size(min = 2, max = 45)
    @Column(length = 45, nullable = false)
    protected String address;
    @Column(length = 11,nullable = false, unique = true, name = "personal_code")
    protected long personalCode;
    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    protected Gender gender;
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

    public long getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(long personalCode) {
        this.personalCode = personalCode;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
