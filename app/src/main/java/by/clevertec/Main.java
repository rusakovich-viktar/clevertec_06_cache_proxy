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

        UserDto user1 = new UserDto();
        user1.setId(1);
        user1.setName("Иван");
        user1.setEmail("ivan@example.com");
        user1.setPhoneNumber("+375291234567");


        UserDto user2 = new UserDto();
        user2.setId(2);
        user2.setName("Петр");
        user2.setEmail("petr@example.com");
        user2.setPhoneNumber("+375291234568");

        userService.saveUser(user1);
        userService.saveUser(user2);

        UserDto user = userService.getUser(1);
        System.out.println(user);
        System.out.println(user);

        user1.setName("Иван Петрович");
        userService.updateUser(user1);

        userService.deleteUser(user1);
    }
}
