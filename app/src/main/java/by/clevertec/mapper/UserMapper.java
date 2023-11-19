package by.clevertec.mapper;

import by.clevertec.dto.UserDto;
import by.clevertec.entity.User;

/**
 * Класс UserMapper предоставляет методы для преобразования между User и UserDto.
 */
public class UserMapper {
    /**
     * Преобразует объект User в UserDto.
     *
     * @param user Объект User для преобразования.
     * @return Объект UserDto, соответствующий переданному объекту User.
     */
    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());

        return userDto;
    }

    /**
     * Преобразует объект UserDto в User.
     *
     * @param userDto Объект UserDto для преобразования.
     * @return Объект User, соответствующий переданному объекту UserDto.
     */
    public User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());

        return user;
    }

}
