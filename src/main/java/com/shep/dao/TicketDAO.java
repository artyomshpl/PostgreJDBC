package com.shep.dao;

import com.shep.entities.Ticket;
import com.shep.enums.TicketType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TicketDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public TicketDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void saveTicket(int userId, TicketType ticketType) {
        Session session = sessionFactory.getCurrentSession();
        //Using raw SQL because casting to enum doesn't work in another way
        String sql = "INSERT INTO Ticket (user_id, ticket_type, creation_date) VALUES (?, CAST(? AS ticket_type), CURRENT_TIMESTAMP)";
        session.createNativeQuery(sql)
                .setParameter(1, userId)
                .setParameter(2, ticketType.name())
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    public Ticket getTicketById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Ticket.class, id);
    }

    @Transactional(readOnly = true)
    public List<Ticket> getTicketsByUserId(int userId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Ticket WHERE user.id = :userId", Ticket.class)
                .setParameter("userId", userId)
                .list();
    }

    @Transactional
    public void updateTicketType(int ticketId, TicketType ticketType) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "UPDATE Ticket SET ticket_type = CAST(? AS ticket_type) WHERE id = ?";
        session.createNativeQuery(sql)
                .setParameter(1, ticketType.name())
                .setParameter(2, ticketId)
                .executeUpdate();
    }
}
