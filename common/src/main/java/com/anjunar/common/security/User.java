package com.anjunar.common.security;

import com.anjunar.common.filedisk.Media;
import com.anjunar.common.i18n.Language;
import org.hibernate.annotations.Filter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Filter(name = "deletedFilter", condition = "deleted = false")
public class User extends Identity implements OwnerProvider {

    private String nickName;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String question;

    private String password;

    @ElementCollection
    private final List<EmailType> emails = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Language language;

    private String token;

    @ManyToMany(fetch = FetchType.EAGER)
    private final Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "source", cascade = CascadeType.ALL)
    private Set<UserRight> rights = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Media background;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

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

    public Set<UserRight> getRights() {
        return rights;
    }

    @Override
    public User getOwner() {
        return this;
    }

    public Media getBackground() {
        return background;
    }

    public void setBackground(Media background) {
        this.background = background;
    }
}
