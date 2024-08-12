package com.shep.dao;

import com.shep.config.DatabaseConfig;
import com.shep.models.Ticket;
import com.shep.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDAO {

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DatabaseConfig.getUrl(), DatabaseConfig.getUser(), DatabaseConfig.getPassword());
    }

    public void setSequenceValue(DatabaseDAO dao, String sequenceName, int value) throws SQLException {
        String sql = "ALTER SEQUENCE " + sequenceName + " RESTART WITH " + value;
        try (Connection conn = dao.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        }
    }

    public void saveUser(String name) throws SQLException {
        String sql = "INSERT INTO \"User\" (name) VALUES (?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    public void saveTicket(int userId, String ticketType) throws SQLException {
        String sql = "INSERT INTO Ticket (user_id, ticket_type) VALUES (?, ?::ticket_type)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, ticketType);
            pstmt.executeUpdate();
        }
    }

    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM \"User\" WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("creation_date"));
            }
        }
        return null;
    }

    public Ticket getTicketById(int id) throws SQLException {
        String sql = "SELECT * FROM Ticket WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Ticket(rs.getInt("id"), rs.getInt("user_id"), rs.getString("ticket_type"), rs.getTimestamp("creation_date"));
            }
        }
        return null;
    }

    public List<Ticket> getTicketsByUserId(int userId) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM Ticket WHERE user_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tickets.add(new Ticket(rs.getInt("id"), rs.getInt("user_id"), rs.getString("ticket_type"), rs.getTimestamp("creation_date")));
            }
        }
        return tickets;
    }

    public void updateTicketType(int ticketId, String ticketType) throws SQLException {
        String sql = "UPDATE Ticket SET ticket_type = ?::ticket_type WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ticketType);
            pstmt.setInt(2, ticketId);
            pstmt.executeUpdate();
        }
    }

    public void deleteUserById(int userId) throws SQLException {
        String sql = "DELETE FROM \"User\" WHERE id = ?";
        try (Connection conn = connect()) {
            conn.setAutoCommit(false);
            Savepoint savepoint = conn.setSavepoint();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                pstmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback(savepoint);
                throw e;
            }
        }
    }

    public void updateUserAndTicket(int userId, String userName, int ticketId, String ticketType) throws SQLException {
        String updateUserSql = "UPDATE \"User\" SET name = ? WHERE id = ?";
        String updateTicketSql = "UPDATE Ticket SET ticket_type = ?::ticket_type WHERE id = ?";

        try (Connection conn = connect()) {
            conn.setAutoCommit(false);
            Savepoint savepoint = conn.setSavepoint();

            try (PreparedStatement updateUserStmt = conn.prepareStatement(updateUserSql);
                 PreparedStatement updateTicketStmt = conn.prepareStatement(updateTicketSql)) {

                updateUserStmt.setString(1, userName);
                updateUserStmt.setInt(2, userId);
                updateUserStmt.executeUpdate();

                updateTicketStmt.setString(1, ticketType);
                updateTicketStmt.setInt(2, ticketId);
                updateTicketStmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback(savepoint);
                throw e;
            }
        }
    }
}
