package com.shep.services;

import com.shep.dao.TicketDAO;
import com.shep.entities.Ticket;
import com.shep.enums.TicketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketDAO ticketDAO;

    @Autowired
    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public void saveTicket(int userId, TicketType ticketType) {
        ticketDAO.saveTicket(userId, ticketType);
    }

    public Ticket getTicketById(int id) {
        return ticketDAO.getTicketById(id);
    }

    public List<Ticket> getTicketsByUserId(int userId) {
        return ticketDAO.getTicketsByUserId(userId);
    }

    public void updateTicketType(int ticketId, TicketType ticketType) {
        ticketDAO.updateTicketType(ticketId, ticketType);
    }
}