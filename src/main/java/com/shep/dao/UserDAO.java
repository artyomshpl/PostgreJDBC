package com.shep.dao;

import com.shep.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class UserDAO {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public void saveUser(String name) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User();
            user.setName(name);
            session.save(user);
            transaction.commit();
        }
    }

    public User getUserById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    public void deleteUserById(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            session.delete(user);
            transaction.commit();
        }
    }

    public void updateUser(int userId, String userName) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            user.setName(userName);
            session.update(user);
            transaction.commit();
        }
    }
}
