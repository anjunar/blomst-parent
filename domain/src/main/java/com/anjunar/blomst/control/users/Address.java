package com.anjunar.blomst.control.users;

import com.anjunar.common.ddd.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Address extends AbstractEntity {

    private String street;

    private String zipCode;

    private String state;

    private String country;

    private Float x;

    private Float y;

    @Column(name = "resume_start")
    private Year start;

    @Column(name = "resume_end")
    private Year end;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Year getStart() {
        return start;
    }

    public void setStart(Year start) {
        this.start = start;
    }

    public Year getEnd() {
        return end;
    }

    public void setEnd(Year end) {
        this.end = end;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }
}
