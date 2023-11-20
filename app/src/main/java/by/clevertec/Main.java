package by.clevertec;

import by.clevertec.dao.UserDao;
import by.clevertec.dao.impl.UserDaoImpl;
import by.clevertec.dto.UserDto;
import by.clevertec.mapper.UserMapperMapStruct;
import by.clevertec.service.UserService;
import by.clevertec.service.impl.UserServiceImpl;
import by.clevertec.validation.UserDtoValidator;
import org.mapstruct.factory.Mappers;

public class Main {

    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();
        UserMapperMapStruct userMapper = Mappers.getMapper(UserMapperMapStruct.class);
        UserDtoValidator userDtoValidator = new UserDtoValidator();
        UserService userService = new UserServiceImpl(userMapper, userDtoValidator, userDao);

        // Создаем нового пользователя
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("Test User");
        userDto.setEmail("Atut@mail.ru");
        userDto.setPhoneNumber("+375447764650");
        userService.saveUser(userDto);

        // Создаем еще одного пользователя
        UserDto anotherUserDto = new UserDto();
        anotherUserDto.setId(2);
        anotherUserDto.setName("Another Test User");
        anotherUserDto.setEmail("Atut@mail.ru");
        anotherUserDto.setPhoneNumber("+375447764650");
        userService.saveUser(anotherUserDto);

        // Получаем пользователя и выводим его
        UserDto retrievedUser = userService.getUser(1);
        if (retrievedUser != null) {
            System.out.println("Retrieved User: " + retrievedUser);

            // Обновляем пользователя и выводим его
            retrievedUser.setName("Updated User");
            userService.updateUser(retrievedUser);
            UserDto updatedUser = userService.getUser(1);
            System.out.println("Updated User: " + updatedUser);

            // Удаляем пользователя
            userService.deleteUser(updatedUser);
            UserDto deletedUser = userService.getUser(1);
            System.out.println("Deleted User: " + deletedUser);
        } else {
            System.out.println("User not found");
        }
    }
}
