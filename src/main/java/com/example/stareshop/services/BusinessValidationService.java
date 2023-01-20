package com.example.stareshop.services;

import com.example.stareshop.model.Business;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class BusinessValidationService implements Validator {
    @Override
    public boolean supports(Class<?> someClass) {
        return Business.class.equals(someClass);
    }

    @Override
    public void validate(Object businessEntity, Errors errors) {
        Business business = (Business) businessEntity;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "business.isNameEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "business.isAddressEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "token", "business.isIdentificationTokenEmpty");

    }
}
