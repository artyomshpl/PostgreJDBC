package com.shep.services;

import com.shep.dao.UserDAO;
import com.shep.entities.User;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public void saveUser(String name) {
        userDAO.saveUser(name);
    }

    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public void deleteUserById(int userId) {
        userDAO.deleteUserById(userId);
    }

    public void updateUser(int userId, String userName) {
        userDAO.updateUser(userId, userName);
    }
}
