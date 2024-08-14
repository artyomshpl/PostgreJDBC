package com.shep.dao;

import com.shep.entities.Ticket;
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
            String sql = "INSERT INTO Ticket (user_id, ticket_type, creation_date) VALUES (?, CAST(? AS ticket_type), CURRENT_TIMESTAMP)";
            session.createNativeQuery(sql)
                    .setParameter(1, userId)
                    .setParameter(2, ticketType.name())
                    .executeUpdate();
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

    public void updateTicketType(int ticketId, TicketType ticketType) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String sql = "UPDATE Ticket SET ticket_type = CAST(? AS ticket_type) WHERE id = ?";
            session.createNativeQuery(sql)
                    .setParameter(1, ticketType.name())
                    .setParameter(2, ticketId)
                    .executeUpdate();
            transaction.commit();
        }
    }
}