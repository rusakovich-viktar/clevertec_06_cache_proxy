package by.clevertec.validation;

import by.clevertec.dto.UserDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

public class UserDtoValidator {

    private final Validator validator;

    public UserDtoValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public void validate(UserDto userDto) {
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<UserDto> violation : violations) {
                System.out.println(violation.getMessage());
            }
        } else {
            System.out.println("Валидация прошла успешно");
        }
    }
}