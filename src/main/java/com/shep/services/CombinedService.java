package com.shep.services;

import com.shep.enums.TicketType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class CombinedService {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public void updateUserAndTicket(int userId, String userName, int ticketId, TicketType ticketType) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            UserService userService = new UserService();
            TicketService ticketService = new TicketService();

            userService.updateUser(userId, userName);
            ticketService.updateTicketType(ticketId, ticketType);

            transaction.commit();
        }
    }
}
