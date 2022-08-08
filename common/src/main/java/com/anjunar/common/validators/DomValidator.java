package com.anjunar.common.validators;

import com.anjunar.common.rest.api.Editor;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class DomValidator implements ConstraintValidator<Dom, Editor> {

    @Override
    public boolean isValid(Editor value, ConstraintValidatorContext context) {
        Document document = Jsoup.parse(value.getHtml());
        Elements scripts = document.getElementsByTag("script");
        return scripts.size() == 0;
    }

}
