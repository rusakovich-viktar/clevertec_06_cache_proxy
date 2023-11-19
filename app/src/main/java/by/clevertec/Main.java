package by.clevertec;

import by.clevertec.dto.UserDto;
import by.clevertec.validation.UserDtoValidator;

public class Main {
    public static void main(String[] args) {
        UserDtoValidator validator = new UserDtoValidator();
        UserDto userDto = new UserDto();

        // Заполните поля userDto
        userDto.setName("Имя");
        userDto.setEmail("email@example.com");
        userDto.setPhoneNumber("+375591234567");

        validator.validate(userDto);
    }
}
