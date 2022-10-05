package com.anjunar.blomst.security.register;

import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class NatualIdValidator implements ConstraintValidator<NaturalId, RegisterForm> {

    private final IdentityManager identityManager;

    @Inject
    public NatualIdValidator(IdentityManager identityManager) {
        this.identityManager = identityManager;
    }

    public NatualIdValidator() {
        this(null);
    }

    @Override
    public boolean isValid(RegisterForm value, ConstraintValidatorContext context) {
        User user = identityManager.findUser(value.getEmail());
        return user == null;
    }

}
