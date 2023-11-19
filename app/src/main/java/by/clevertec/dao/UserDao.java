package by.clevertec.dao;

import by.clevertec.entity.User;
import java.util.List;

public interface UserDao {
    User get(int id);

    List<User> getAll();

    void save(User user);

    void update(User user);

    void delete(User user);
}
