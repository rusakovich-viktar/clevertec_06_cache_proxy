package by.clevertec.dto;

import static by.clevertec.util.Constants.Attributes.PHONE_PATTERN;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDto {
    private int id;
    @NotNull
    @Size(min = 1, max = 30, message = "Name should not be more 30 character")
    private String name;
    @Email(message = "Неверный формат электронной почты")
    private String email;
    @Pattern(regexp = PHONE_PATTERN, message = "Введите номер в международном формате: +375хх1234567")
    private String phoneNumber;
}
