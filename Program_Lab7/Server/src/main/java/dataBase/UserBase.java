package dataBase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UserBase {
    private final Connection connection;
    private Statement statement;

    public UserBase(Connection connection) throws SQLException {
        this.connection = connection;
        this.statement = connection.createStatement();
        String createTable = "CREATE TABLE IF NOT EXISTS Users" +
                "(Login VARCHAR(30) NOT NULL PRIMARY KEY , Password TEXT NOT NULL)";
        statement.execute(createTable);
    }

    public boolean isExisting(String login) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT Login FROM Users");
        while (resultSet.next()) {
            if (resultSet.getString("Login").equals(login)) { return true; }
        }
        return false;
    }

    public boolean validation(String login, String pass) throws SQLException {
        String query = "SELECT * FROM Users";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            if (resultSet.getString("Login").equals(login) && resultSet.getString("Password").equals(HashPass(pass))) {
                return true;
            }
        }
        return false;
    }

    public void addUser(String login, String pass) throws SQLException {
        String query = "INSERT INTO Users (Login, Password) VALUES ('" + login + "', '" + HashPass(pass) +"')";
        statement.execute(query);
    }

    public static String HashPass(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-224");
            byte[] hash = md.digest(pass.getBytes());
            String hashText = new String(hash);
            hashText = "t1u7" + hashText + "a1n2";
            return hashText;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Can't find the correspond hashing algorithm!");
            throw new IllegalStateException(e);
        }
    }
}
