package com.anjunar.blomst.control.users;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.security.User;
import jakarta.persistence.*;

import java.time.Year;

@Entity
public class Address extends AbstractEntity {

    @ManyToOne
    private User owner;

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

    @OneToOne(mappedBy = "source", cascade = CascadeType.ALL)
    private AddressRight right;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

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

    public AddressRight getRight() {
        return right;
    }

    public void setRight(AddressRight right) {
        this.right = right;
    }
}
