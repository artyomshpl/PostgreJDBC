package com.shep.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.*;

@Getter
@Setter
@AllArgsConstructor
public class Ticket {
    private int id;
    private int userId;
    private String ticketType;
    private Timestamp creationDate;
}
