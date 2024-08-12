package com.shep;

import com.shep.dao.DatabaseDAO;
import com.shep.models.Ticket;
import com.shep.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class PostgreApplication {

    public static void main(String[] args) {
        DatabaseDAO dao = new DatabaseDAO();

        try {
            dao.setSequenceValue(dao, "user_id_seq", 1);
            dao.setSequenceValue(dao, "ticket_id_seq", 1);

            dao.saveUser("Test user");
            System.out.println("User saved successfully.");

            User user = dao.getUserById(1);
            if (user != null) {
                System.out.println("User found: " + user.getName());
            } else {
                System.out.println("User not found.");
                return;
            }

            dao.saveTicket(user.getId(), "DAY");
            System.out.println("Ticket saved successfully.");

            Ticket ticket = dao.getTicketById(1);
            if (ticket != null) {
                System.out.println("Ticket found: " + ticket.getTicketType());
            } else {
                System.out.println("Ticket not found.");
            }

            List<Ticket> tickets = dao.getTicketsByUserId(user.getId());
            System.out.println("Tickets for user " + user.getName() + ":");
            for (Ticket t : tickets) {
                System.out.println("Ticket ID: " + t.getId() + ", Type: " + t.getTicketType());
            }

            dao.updateUserAndTicket(user.getId(), "Updated User", ticket.getId(), "WEEK");
            System.out.println("User and ticket updated successfully.");

            dao.deleteUserById(user.getId());
            System.out.println("User and tickets deleted successfully.");

            Random random = new Random();
            for (int i = 0; i < 3; i++) {
                String randomName = "RandomUser" + random.nextInt(1000);
                dao.saveUser(randomName);
                System.out.println("Random user saved: " + randomName);

                User randomUser = dao.getUserById(i + 2);
                if (randomUser != null) {
                    for (int j = 0; j < 2; j++) {
                        String ticketType = (j % 2 == 0) ? "DAY" : "WEEK";
                        dao.saveTicket(randomUser.getId(), ticketType);
                        System.out.println("Ticket saved for user " + randomUser.getName() + ": " + ticketType);
                    }
                } else {
                    System.out.println("Random user not found: " + randomName);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}