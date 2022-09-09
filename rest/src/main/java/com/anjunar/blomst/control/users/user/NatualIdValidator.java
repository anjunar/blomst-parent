package com.anjunar.blomst.control.users.user;

import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class NatualIdValidator implements ConstraintValidator<NaturalId, UserForm> {

    private final IdentityManager identityManager;

    @Inject
    public NatualIdValidator(IdentityManager identityManager) {
        this.identityManager = identityManager;
    }

    public NatualIdValidator() {
        this(null);
    }

    @Override
    public boolean isValid(UserForm value, ConstraintValidatorContext context) {
        User user = identityManager.findUser(value.getFirstName(), value.getLastName(), value.getBirthDate());
        if (user == null) {
            return true;
        } else {
            return user.getId().equals(value.getId());
        }
    }

}
