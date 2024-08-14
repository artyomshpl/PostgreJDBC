package com.shep;

import com.shep.entities.Ticket;
import com.shep.entities.User;
import com.shep.enums.TicketType;
import com.shep.services.CombinedService;
import com.shep.services.TicketService;
import com.shep.services.UserService;

import java.util.List;
import java.util.Random;

public class PostgreApplication {

    public static void main(String[] args) {
        UserService userService = new UserService();
        TicketService ticketService = new TicketService();
        CombinedService combinedService = new CombinedService();

        userService.saveUser("Test user");
        System.out.println("User saved successfully.");

        User user = userService.getUserById(2);
        if (user != null) {
            System.out.println("User found: " + user.getName());
        } else {
            System.out.println("User not found.");
            return;
        }

        ticketService.saveTicket(user.getId(), TicketType.DAY);
        System.out.println("Ticket saved successfully.");

        Ticket ticket = ticketService.getTicketById(2);
        if (ticket != null) {
            System.out.println("Ticket found: " + ticket.getTicketType());
        } else {
            System.out.println("Ticket not found.");
        }

        List<Ticket> tickets = ticketService.getTicketsByUserId(user.getId());
        System.out.println("Tickets for user " + user.getName() + ":");
        for (Ticket t : tickets) {
            System.out.println("Ticket ID: " + t.getId() + ", Type: " + t.getTicketType());
        }

        combinedService.updateUserAndTicket(user.getId(), "Updated User", ticket.getId(), TicketType.WEEK);
        System.out.println("User and ticket updated successfully.");

        userService.deleteUserById(user.getId());
        System.out.println("User and tickets deleted successfully.");

        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            String randomName = "RandomUser" + random.nextInt(1000);
            userService.saveUser(randomName);
            System.out.println("Random user saved: " + randomName);

            User randomUser = userService.getUserById(i + 2);
            if (randomUser != null) {
                for (int j = 0; j < 2; j++) {
                    TicketType ticketType = (j % 2 == 0) ? TicketType.DAY : TicketType.WEEK;
                    ticketService.saveTicket(randomUser.getId(), ticketType);
                    System.out.println("Ticket saved for user " + randomUser.getName() + ": " + ticketType);
                }
            } else {
                System.out.println("Random user not found: " + randomName);
            }
        }
    }
}