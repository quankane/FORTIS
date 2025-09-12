package vn.com.fortis.domain.validator;

import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.domain.entity.user.Gender;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenderSubsetValidator.class)
public @interface GenderSubset {

    Gender[] anyOf();
    String message() default ErrorMessage.Validator.ERR_GENDER_VALIDATOR;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
