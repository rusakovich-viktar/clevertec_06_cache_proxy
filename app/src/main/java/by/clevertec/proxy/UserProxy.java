package by.clevertec.proxy;

import by.clevertec.cache.Cache;
import by.clevertec.cache.impl.LfuCache;
import by.clevertec.cache.impl.LruCache;
import by.clevertec.dto.UserDto;
import by.clevertec.entity.User;
import by.clevertec.mapper.UserMapperMapStruct;
import by.clevertec.util.YamlReader;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.mapstruct.factory.Mappers;

/**
 * Класс {@code UserProxy} представляет собой прокси для работы с пользователями.
 * Он использует аспектно-ориентированное программирование (AOP) для кэширования пользователей.
 * <p>
 * Этот класс использует {@code Cache<Integer, User>} для хранения пользователей, где ключом является идентификатор пользователя.
 * Кэш настраивается с помощью метода {@code configureCache()}, который читает конфигурацию из файла YAML.
 * <p>
 * Методы {@code getUser()}, {@code createUser()}, {@code deleteUser()} и {@code updateUser()} объявлены как точки среза (pointcuts) для AOP.
 * Они используются в аннотациях {@code Around} и {@code AfterReturning} для выполнения действий перед, после или вместо вызова соответствующих методов сервиса.
 * <p>
 * Каждый из этих методов записывает информацию в журнал и обновляет кэш соответствующим образом.
 */
@Aspect
@Log4j2
@NoArgsConstructor
public class UserProxy {

    private Cache<Integer, User> userCache = configureCache();
    private final UserMapperMapStruct userMapper = Mappers.getMapper(UserMapperMapStruct.class);


    @Pointcut("@annotation(by.clevertec.proxy.annotation.Cacheable)")
    public void getUser() {
    }

    @Pointcut("@annotation(by.clevertec.proxy.annotation.Cacheable)")
    public void createUser() {
    }

    @Pointcut("@annotation(by.clevertec.proxy.annotation.Cacheable) && execution(* by.clevertec.service.UserService.deleteUser(..)) ")
    public void deleteUser() {
    }

    @Pointcut("@annotation(by.clevertec.proxy.annotation.Cacheable) && execution(* by.clevertec.service.UserService.updateUser(..))")
    public void updateUser() {
    }

    /**
     * Метод {@code getUser} объявлен как точка среза (pointcut) для AOP.
     * Он используется в аннотации {@code Around} для выполнения действий перед вызовом соответствующего метода сервиса.
     *
     * @param joinPoint Объект {@code ProceedingJoinPoint}, предоставляющий информацию о вызове метода.
     * @param id        Идентификатор пользователя.
     * @return Объект {@code UserDto}, если пользователь найден в кэше, иначе результат вызова исходного метода.
     * @throws Throwable Если происходит исключение во время выполнения исходного метода.
     */
    @Around("getUser() && args(id)")
    public Object getUser(ProceedingJoinPoint joinPoint, int id) throws Throwable {
        log.info("Entering getUser advice with id: {}", id);
        User user = userCache.get(id);
        if (user != null) {
            log.info("we in cache getUser");
            return userMapper.convertToDto(user);
        }
        Object result = joinPoint.proceed();
        if (result instanceof UserDto userDto) {
            log.info("joinPoint.proceed getUser");
            userCache.put(id, userMapper.convertToEntity(userDto));
            return userDto;
        }
        return result;
    }

    /**
     * Метод {@code createUser} объявлен как точка среза (pointcut) для AOP.
     * Он используется в аннотации {@code AfterReturning} для выполнения действий после успешного выполнения соответствующего метода сервиса.
     *
     * @param userDto Объект {@code UserDto}, который был передан в исходный метод.
     */
    @AfterReturning(pointcut = "createUser() && execution(* by.clevertec.service.UserService.saveUser(..)) && args(userDto)")
    public void createUser(UserDto userDto) {
        log.info("we in aspect createUser");
        User user = userMapper.convertToEntity(userDto);
        userCache.put(user.getId(), user);
    }

    /**
     * Метод {@code deleteUser} объявлен как точка среза (pointcut) для AOP.
     * Он используется в аннотации {@code AfterReturning} для выполнения действий после успешного выполнения соответствующего метода сервиса.
     *
     * @param userDto Объект {@code UserDto}, который был передан в исходный метод.
     */
    @AfterReturning(pointcut = "deleteUser() && args(userDto)")
    public void deleteUser(UserDto userDto) {
        log.info("we in aspect deleteUser");
        User user = userMapper.convertToEntity(userDto);
        userCache.remove(user.getId());
    }

    /**
     * Метод {@code updateUser} объявлен как точка среза (pointcut) для AOP.
     * Он используется в аннотации {@code AfterReturning} для выполнения действий после успешного выполнения соответствующего метода сервиса.
     *
     * @param userDto Объект {@code UserDto}, который был передан в исходный метод.
     */
    @AfterReturning(pointcut = "updateUser() && args(userDto)")
    public void updateUser(UserDto userDto) {
        log.info("we in aspect updateUser");
        User user = userMapper.convertToEntity(userDto);
        userCache.put(user.getId(), user);
    }

    /**
     * Метод {@code configureCache} используется для настройки кэша.
     * Он читает конфигурацию из файла YAML и создает кэш с выбранным типом и вместимостью.
     *
     * @return Объект {@code Cache<Integer, User>}, представляющий настроенный кэш.
     */
    private Cache<Integer, User> configureCache() {
        if (userCache == null) {
            synchronized (this) {
                String algorithm = YamlReader.chooseCacheType();
                int capacity = Integer.parseInt(YamlReader.chooseCacheCapacity());
                if ("LFU".equals(algorithm)) {
                    userCache = new LfuCache<>(capacity);
                } else {
                    userCache = new LruCache<>(capacity);
                }
            }
        }
        return userCache;
    }
}
