package com.shep.dao;

import com.shep.entities.Ticket;
import com.shep.entities.User;
import com.shep.enums.TicketType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class TicketDAO {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public void saveTicket(int userId, TicketType ticketType) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            Ticket ticket = new Ticket();
            ticket.setUser(user);
            ticket.setTicketType(ticketType);
            session.save(ticket);
            transaction.commit();
        }
    }

    public Ticket getTicketById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Ticket.class, id);
        }
    }

    public List<Ticket> getTicketsByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Ticket WHERE user.id = :userId", Ticket.class)
                    .setParameter("userId", userId)
                    .list();
        }
    }
}