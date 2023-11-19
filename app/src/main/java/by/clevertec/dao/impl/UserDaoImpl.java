package by.clevertec.dao.impl;

import by.clevertec.dao.UserDao;
import by.clevertec.entity.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.ToString;

@ToString
public class UserDaoImpl implements UserDao {
    private Map<Integer, User> users = new HashMap<>();

    @Override
    public User get(int id) {
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void save(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void update(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void delete(User user) {
        users.remove(user.getId());
    }
}
