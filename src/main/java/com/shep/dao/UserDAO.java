package com.shep.dao;

import com.shep.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void saveUser(String name) {
        Session session = sessionFactory.getCurrentSession();
        User user = new User();
        user.setName(name);
        session.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }

    @Transactional
    public void deleteUserById(int userId) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, userId);
        session.delete(user);
    }

    @Transactional
    public void updateUser(int userId, String userName) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, userId);
        user.setName(userName);
        session.update(user);
    }
}
