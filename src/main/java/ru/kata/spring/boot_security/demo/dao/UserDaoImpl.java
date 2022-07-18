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
    //private  LocalContainerEntityManagerFactoryBean em;

    /*
    @Autowired
    public void setEm(LocalContainerEntityManagerFactoryBean em) {
        this.em = em;
    }

     */

    /*private PlatformTransactionManager platformTransactionManager;

    @Autowired
    public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }*/


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
        //EntityManagerFactory entityManagerFactory = em.getObject();
        //EntityManager entityManager = entityManagerFactory.createEntityManager();

        //EntityTransaction transaction = entityManager.getTransaction();
        //transaction.begin();
        entityManager.persist(user);
        //entityManager.flush();
        //transaction.commit();
    }

    @Override
    public List<User> listUsers() {
        TypedQuery<User> query = /*em.getObject().createEntityManager()*/entityManager.createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public User getByID(long id) {
        //EntityManagerFactory entityManagerFactory = em.getObject();
        //EntityManager entityManager = entityManagerFactory.createEntityManager();
        //EntityTransaction transaction = entityManager.getTransaction();
        //transaction.begin();
        User user = entityManager.find(User.class, id);
        //entityManager.flush();
        //transaction.commit();
        //entityManager.close();
        return user;
    }

    @Override
    public void edit(User user) {
        //EntityManagerFactory entityManagerFactory = em.getObject();
        //EntityManager entityManager = entityManagerFactory.createEntityManager();
        //EntityTransaction transaction = entityManager.getTransaction();
        //transaction.begin();
        entityManager.merge(user);
        //entityManager.flush();
        //transaction.commit();
        //entityManager.close();
    }

    @Override
    public void delete(User user) {
        //EntityManagerFactory entityManagerFactory = em.getObject();
        //EntityManager entityManager = entityManagerFactory.createEntityManager();
        //EntityTransaction transaction = entityManager.getTransaction();
        //transaction.begin();
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
        //entityManager.flush();
        //transaction.commit();
        //entityManager.close();
    }
}
