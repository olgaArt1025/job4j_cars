package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.User;
import ru.job4j.store.UserStore;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {
    private final UserStore store;

    public UserService(UserStore store) {
        this.store = store;
    }

    public Collection<User> findAll() {
        return store.findAll();
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public User findById(Integer id) {
        return store.findById(id);
    }

    public Optional<User> findUserByNameAndPwd(String name, String password) {
        return store.findUserByNameAndPwd(name, password);
    }
}
