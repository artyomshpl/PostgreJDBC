package com.shep.services;

import com.shep.dao.UserDAO;
import com.shep.entities.User;
import com.shep.enums.TicketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final TicketService ticketService;

    @Autowired
    public UserService(UserDAO userDAO, TicketService ticketService) {
        this.userDAO = userDAO;
        this.ticketService = ticketService;
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

    @Transactional
    public void updateUserAndTicket(int userId, String userName, int ticketId, TicketType ticketType) {
        userDAO.updateUser(userId, userName);
        ticketService.updateTicketType(ticketId, ticketType);
    }
}
