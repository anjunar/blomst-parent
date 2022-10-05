package com.anjunar.common.security;

import com.anjunar.common.i18n.Language;
import org.hibernate.annotations.Filter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Filter(name = "deletedFilter", condition = "deleted = false")
public class User extends Identity {

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String question;

    private String password;

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }

    @ElementCollection
    private final List<EmailType> emails = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Language language;

    private String token;

    @ManyToMany(fetch = FetchType.EAGER)
    private final Set<Role> roles = new HashSet<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthdate) {
        this.birthDate = birthdate;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<EmailType> getEmails() {
        return emails;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<Role> getRoles() {
        return roles;
    }

}
