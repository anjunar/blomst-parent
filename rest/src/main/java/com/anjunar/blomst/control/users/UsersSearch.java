package com.anjunar.blomst.control.users;

import com.anjunar.common.rest.api.DateDuration;
import com.anjunar.common.rest.search.provider.*;
import com.anjunar.common.rest.search.AbstractRestSearch;
import com.anjunar.common.rest.search.RestPredicate;
import com.anjunar.common.rest.search.RestSort;

import jakarta.ws.rs.QueryParam;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class UsersSearch extends AbstractRestSearch {

    @RestSort(GenericSortProvider.class)
    @QueryParam("sort")
    private List<String> sort;

    @RestPredicate(FirstNameProvider.class)
    @QueryParam("firstName")
    private String firstName;

    @RestPredicate(LastNameProvider.class)
    @QueryParam("lastName")
    private String lastName;

    @RestPredicate(BirthDateProvider.class)
    @QueryParam("birthDate")
    private DateDuration birthDate;

    @RestPredicate(EmailProvider.class)
    @QueryParam("emails")
    private String emails;

    @RestPredicate(GenericManyToOneProvider.class)
    @QueryParam("language")
    private UUID language;

    @RestPredicate(GenericOneToManyProvider.class)
    @QueryParam("roles")
    private Set<UUID> roles;

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
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

    public DateDuration getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(DateDuration birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public UUID getLanguage() {
        return language;
    }

    public void setLanguage(UUID language) {
        this.language = language;
    }

    public Set<UUID> getRoles() {
        return roles;
    }

    public void setRoles(Set<UUID> roles) {
        this.roles = roles;
    }
}
