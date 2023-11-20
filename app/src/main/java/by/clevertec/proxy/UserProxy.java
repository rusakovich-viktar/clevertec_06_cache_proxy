package by.clevertec.proxy;

import by.clevertec.cache.Cache;
import by.clevertec.cache.impl.LFUCache;
import by.clevertec.cache.impl.LRUCache;
import by.clevertec.dto.UserDto;
import by.clevertec.entity.User;
import by.clevertec.mapper.UserMapper;
import by.clevertec.util.YamlReader;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Log4j2
public class UserProxy {

    private final Cache<Integer, User> userCache;
    private final UserMapper userMapper;

    public UserProxy() {
        String algorithm = YamlReader.chooseCacheType();
        int capacity = Integer.parseInt(YamlReader.chooseCacheCapacity());

        if ("LFU".equalsIgnoreCase(algorithm)) {
            this.userCache = new LFUCache<>(capacity);
        } else if ("LRU".equalsIgnoreCase(algorithm)) {
            this.userCache = new LRUCache<>(capacity);
        } else {
            throw new IllegalArgumentException();
        }

        this.userMapper = new UserMapper();
    }

    @Pointcut("@annotation(by.clevertec.proxy.annotation.Cacheable)")
    public void getUser() {
    }

    @Pointcut("@annotation(by.clevertec.proxy.annotation.Cacheable)")
    public void createUser() {
    }

    @Pointcut("@annotation(by.clevertec.proxy.annotation.Cacheable)")
    public void deleteUser() {
    }

    @Pointcut("@annotation(by.clevertec.proxy.annotation.Cacheable)")
    public void updateUser() {
    }

    @Around("getUser() && args(id)")
    public Object getUser(ProceedingJoinPoint joinPoint, int id) throws Throwable {
        log.info("Entering getUser advice with id: {}", id);
        User user = userCache.get(id);
        if (user != null) {
            return userMapper.convertToDto(user);
        }

        Object result = joinPoint.proceed();
        if (result instanceof UserDto userDto) {
            userCache.put(id, userMapper.convertToEntity(userDto));
            return userDto;
        }
        return null;
    }

    @AfterReturning(pointcut = "createUser() && args(userDto)", returning = "userDto")
    public void createUser(UserDto userDto) {
        User user = userMapper.convertToEntity(userDto);
        userCache.put(user.getId(), user);
    }

    @AfterReturning(pointcut = "deleteUser() && args(userDto)", returning = "userDto")
    public void deleteUser(UserDto userDto) {
        User user = userMapper.convertToEntity(userDto);
        userCache.remove(user.getId());
    }

    @AfterReturning(pointcut = "updateUser() && args(userDto)", returning = "userDto")
    public void updateUser(UserDto userDto) {
        User user = userMapper.convertToEntity(userDto);
        userCache.put(user.getId(), user);
    }
}
