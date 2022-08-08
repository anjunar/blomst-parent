package com.anjunar.blomst.security.register;

import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class NatualIdValidator implements ConstraintValidator<NaturalId, RegisterForm> {

    private final IdentityProvider identityProvider;

    @Inject
    public NatualIdValidator(IdentityProvider identityProvider) {
        this.identityProvider = identityProvider;
    }

    public NatualIdValidator() {
        this(null);
    }

    @Override
    public boolean isValid(RegisterForm value, ConstraintValidatorContext context) {
        User user = identityProvider.findUser(value.getFirstName(), value.getLastName(), value.getBirthDate());
        return user == null;
    }

}
