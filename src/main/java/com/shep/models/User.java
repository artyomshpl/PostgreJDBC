package com.shep.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.*;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private Timestamp creationDate;
}
