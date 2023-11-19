package by.clevertec.service.impl;

import by.clevertec.dao.UserDao;
import by.clevertec.dao.impl.UserDaoImpl;
import by.clevertec.dto.UserDto;
import by.clevertec.entity.User;
import by.clevertec.mapper.UserMapper;
import by.clevertec.service.UserService;
import by.clevertec.validation.UserDtoValidator;
import java.util.List;
import java.util.stream.Collectors;


public class UserServiceImpl implements UserService {

    UserMapper userMapper = new UserMapper();
    UserDtoValidator userDtoValidator = new UserDtoValidator();


    private final UserDao userDao = new UserDaoImpl();

    @Override
    public UserDto getUser(int id) {
        User user = userDao.get(id);
        return user != null ? userMapper.convertToDto(user) : null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userDao.getAll();
        return users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void saveUser(UserDto userDto) {
        userDtoValidator.validate(userDto);
        User user = userMapper.convertToEntity(userDto);
        userDao.save(user);
    }

    @Override
    public void updateUser(UserDto userDto) {
        userDtoValidator.validate(userDto);
        User user = userMapper.convertToEntity(userDto);
        userDao.update(user);
    }

    @Override
    public void deleteUser(UserDto userDto) {
        User user = userMapper.convertToEntity(userDto);
        userDao.delete(user);
    }

}