package by.clevertec.proxy;

import by.clevertec.cache.Cache;
import by.clevertec.cache.impl.LFUCache;
import by.clevertec.cache.impl.LRUCache;
import by.clevertec.dao.UserDao;
import by.clevertec.dto.UserDto;
import by.clevertec.entity.User;
import by.clevertec.mapper.UserMapper;
import by.clevertec.util.YamlReader;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Log4j2
public class UserProxy {

    private final Cache<Integer, User> userCache;
    private final UserMapper userMapper;
    private final UserDao userDao;

    public UserProxy(UserMapper userMapper, UserDao userDao) {
        String algorithm = YamlReader.chooseCacheType();
        int capacity = Integer.parseInt(YamlReader.chooseCacheCapacity());

        if ("LFU".equalsIgnoreCase(algorithm)) {
            this.userCache = new LFUCache<>(capacity);
        } else if ("LRU".equalsIgnoreCase(algorithm)) {
            this.userCache = new LRUCache<>(capacity);
        } else {
            throw new IllegalArgumentException();
        }

        this.userMapper = userMapper;
        this.userDao = userDao;
    }

    @Pointcut("@annotation(by.clevertec.proxy.annotation.GetUser)")
    public void getUser() {
    }

    @Pointcut("@annotation(by.clevertec.proxy.annotation.CreateUser)")
    public void createUser() {
    }

    @Pointcut("@annotation(by.clevertec.proxy.annotation.DeleteUser)")
    public void deleteUser() {
    }

    @Pointcut("@annotation(by.clevertec.proxy.annotation.UpdateUser)")
    public void updateUser() {
    }

    @Around("getUser() && args(id)")
    public Object getUser(ProceedingJoinPoint joinPoint, int id) throws Throwable {
        log.info("Entering getUser advice with id: {}", id);
        User user = userCache.get(id);
        if (user != null) {
            return userMapper.convertToDto(user);
        }

        UserDto result = (UserDto) joinPoint.proceed();
        if (result != null) {
            userCache.put(id, userMapper.convertToEntity(result));
        }
        return result;
    }

    @Around("createUser() && args(userDto)")
    public Object createUser(ProceedingJoinPoint joinPoint, UserDto userDto) throws Throwable {
        User user = userMapper.convertToEntity(userDto);
        userCache.put(user.getId(), user);
        return joinPoint.proceed();
    }

    @Around("deleteUser() && args(userDto)")
    public Object deleteUser(ProceedingJoinPoint joinPoint, UserDto userDto) throws Throwable {
        User user = userMapper.convertToEntity(userDto);
        userCache.remove(user.getId());
        return joinPoint.proceed();
    }

    @Around("updateUser() && args(userDto)")
    public Object updateUser(ProceedingJoinPoint joinPoint, UserDto userDto) throws Throwable {
        User user = userMapper.convertToEntity(userDto);
        userDao.update(user);  // обновляем данные в DAO
        userCache.put(user.getId(), user);  // обновляем данные в кэше
        return userDto;
    }
}
