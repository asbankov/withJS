package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public User findByUsername(String username) {
        User user = null;
        TypedQuery<User> query = entityManager.createNamedQuery("from User u where u.username = :username", User.class);
        query.setParameter("username", username);
        return user = query.getSingleResult();
    }

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);

    }

    @Override
    public List<User> listUsers() {
        TypedQuery<User> query = entityManager.createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public User getByID(long id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

    @Override
    public void edit(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(User user) {
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));

    }
}
