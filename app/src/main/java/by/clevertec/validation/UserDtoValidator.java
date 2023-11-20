package by.clevertec.validation;

import by.clevertec.dto.UserDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import lombok.extern.log4j.Log4j2;

/**
 * Класс UserDtoValidator предоставляет метод для валидации объектов UserDto.
 * Этот класс использует Validator из Jakarta Validation для проверки ограничений, определенных в классе UserDto.
 */
@Log4j2
public class UserDtoValidator {

    private final Validator validator;

    /**
     * Конструктор класса UserDtoValidator.
     * Инициализирует Validator из Jakarta Validation.
     */
    public UserDtoValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    /**
     * Проверяет объект UserDto на соответствие ограничениям, определенным в классе UserDto.
     * Если есть какие-либо нарушения ограничений, выводит их в консоль.
     * Если нет нарушений, выводит сообщение о том, что валидация прошла успешно.
     *
     * @param userDto Объект UserDto для валидации.
     */
    public void validate(UserDto userDto) {
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<UserDto> violation : violations) {
                log.info(violation.getMessage());
            }
        }
    }
}
