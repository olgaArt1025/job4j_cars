package ru.job4j.store;

import org.hibernate.SessionFactory;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;


@Repository
public class UserStore implements Store {
    private final SessionFactory sf;

    public UserStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<User> findAll() {
        return this.tx(
                session -> session.createQuery("from User").list(),
                sf
        );
    }

    public Optional<User> add(User user) {
        return this.tx(
                session -> {
                    session.save(user);
                  return   session.createQuery("from User").uniqueResultOptional();
                },
                sf
        );
    }


    public Optional<User> update(int id, User user) {
        return this.tx(
                session -> {
                    user.setId(id);
                    session.update(user);
                    return session.createQuery("from User").uniqueResultOptional();
                },
                sf
        );
    }

    public User findById(int id) {
        return this.tx(
                session -> session.get(User.class, id),
                sf
        );
    }

    public Optional<User> findUserByNameAndPwd(String name, String password) {
        return this.tx(
                session -> {
                    Query query = session.createQuery("from User u where u.name = :newName and u.password = :newPassword");
                    query.setParameter("newName", name);
                    query.setParameter("newPassword", password);
                    return query.uniqueResultOptional();
                },
                sf
        );
    }
 }
