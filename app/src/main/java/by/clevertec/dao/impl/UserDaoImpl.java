package by.clevertec.dao.impl;

import by.clevertec.dao.UserDao;
import by.clevertec.entity.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.ToString;

/**
 * Класс UserDaoImpl реализует интерфейс UserDao и предоставляет базовые операции CRUD (создание, чтение, обновление, удаление) для пользователей.
 * Этот класс использует Map<Integer, User> для хранения пользователей, где ключ - это ID пользователя, а значение - сам пользователь.
 */
@ToString
public class UserDaoImpl implements UserDao {
    private final Map<Integer, User> users = new HashMap<>();

    /**
     * Возвращает пользователя с указанным ID.
     *
     * @param id ID пользователя.
     * @return Пользователь с указанным ID или null, если такого пользователя нет.
     */
    @Override
    public User get(int id) {
        return users.get(id);
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return Список всех пользователей.
     */

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    /**
     * Сохраняет пользователя в Map.
     *
     * @param user Пользователь для сохранения.
     */
    @Override
    public void save(User user) {
        users.put(user.getId(), user);
    }

    /**
     * Обновляет пользователя в Map.
     *
     * @param user Пользователь для обновления.
     */
    @Override
    public void update(User user) {
        users.put(user.getId(), user);
    }

    /**
     * Удаляет пользователя из Map.
     *
     * @param user Пользователь для удаления.
     */
    @Override
    public void delete(User user) {
        users.remove(user.getId());
    }
}
