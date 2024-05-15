package com.alura.chalenge.application.courses.validators;

import com.alura.chalenge.application.shared.enums.Status;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StatusEnumValidatorImpl implements ConstraintValidator<StatusEnumValidator, Status> {
    private Status[] subset;

    @Override
    public void initialize(StatusEnumValidator constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(Status value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}
