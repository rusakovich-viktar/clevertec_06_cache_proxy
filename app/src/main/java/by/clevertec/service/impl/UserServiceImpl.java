package by.clevertec.service.impl;

import by.clevertec.dao.UserDao;
import by.clevertec.dao.impl.UserDaoImpl;
import by.clevertec.dto.UserDto;
import by.clevertec.entity.User;
import by.clevertec.mapper.UserMapperMapStruct;
import by.clevertec.service.UserService;
import by.clevertec.validation.UserDtoValidator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;

/**
 * Класс UserServiceImpl реализует интерфейс UserService и предоставляет базовые операции CRUD (создание, чтение, обновление, удаление) для пользователей.
 * Этот класс использует UserDao для взаимодействия с базой данных и UserMapper для преобразования между User и UserDto.
 * Кроме того, он использует UserDtoValidator для проверки UserDto перед сохранением или обновлением.
 */

@NoArgsConstructor
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserMapperMapStruct userMapper = Mappers.getMapper(UserMapperMapStruct.class);
    UserDtoValidator userDtoValidator = new UserDtoValidator();
    UserDao userDao = new UserDaoImpl();

    /**
     * Возвращает UserDto для пользователя с указанным ID.
     *
     * @param id ID пользователя.
     * @return UserDto для пользователя с указанным ID или null, если такого пользователя нет.
     */
    @Override
    public UserDto getUser(int id) {
        User user = userDao.get(id);
        return user != null ? userMapper.convertToDto(user) : null;
    }

    /**
     * Возвращает список всех пользователей в виде UserDto.
     *
     * @return Список всех пользователей в виде UserDto.
     */
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userDao.getAll();
        return users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    /**
     * Сохраняет пользователя в базе данных. Перед сохранением UserDto проверяется на валидность.
     *
     * @param userDto UserDto пользователя для сохранения.
     */
    @Override
    public void saveUser(UserDto userDto) {
        userDtoValidator.validate(userDto);
        User user = userMapper.convertToEntity(userDto);
        userDao.save(user);
    }

    /**
     * Обновляет пользователя в базе данных. Перед обновлением UserDto проверяется на валидность.
     *
     * @param userDto UserDto пользователя для обновления.
     */
    @Override
    public void updateUser(UserDto userDto) {
        userDtoValidator.validate(userDto);
        User user = userMapper.convertToEntity(userDto);
        userDao.update(user);
    }

    /**
     * Удаляет пользователя из базы данных.
     *
     * @param userDto UserDto пользователя для удаления.
     */
    @Override
    public void deleteUser(UserDto userDto) {
        User user = userMapper.convertToEntity(userDto);
        userDao.delete(user);
    }

}