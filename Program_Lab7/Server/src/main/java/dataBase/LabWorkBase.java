package dataBase;

import labWork.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class LabWorkBase {
    private final Connection connection;
    private Statement statement;

    public LabWorkBase(Connection connection) throws SQLException {
        this.connection = connection;
        this.statement = connection.createStatement();
        //String query = "DROP TABLE LabWork";
        //statement.execute(query);
        String createBase = "CREATE TABLE IF NOT EXISTS LabWork(ID BIGSERIAL NOT NULL PRIMARY KEY," +
                "Login VARCHAR(30) NOT NULL, Lab_Name VARCHAR(30) NOT NULL, Coord_X INT NOT NULL, " +
                "Coord_Y FLOAT NOT NULL, Creation_Date VARCHAR(30) NOT NULL, Minimal_Point BIGINT NOT NULL, " +
                "Tuned_In_Works BIGINT, Average_Point INT, Difficulty VARCHAR(10), Author_Name VARCHAR(100), " +
                "Author_Weight REAL, Local_X INT, Local_Y REAL, Local_Z FLOAT, Local_Name VARCHAR(236), " +
                "CONSTRAINT User_Lab FOREIGN KEY (Login) REFERENCES Users(Login))";
        statement.execute(createBase);
    }

    public boolean validOwner(Long id, String login) throws SQLException {
        String query = "SELECT ID, Login FROM LabWork";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            if (resultSet.getLong("ID") == id && resultSet.getString("Login").equals(login)) {
                resultSet.close();
                return true;
            }
        }
        resultSet.close();
        return false;
    }

    public void clearBase(String login) throws SQLException {
        String query = "DELETE FROM LabWork WHERE Login = '" + login + "'";
        statement.executeUpdate(query);
    }

    public Map<Integer, LabWork> getMap() throws SQLException {
        String query = "SELECT * FROM LabWork";
        ResultSet resultSet = statement.executeQuery(query);
        Map<Integer, LabWork> collection = Collections.synchronizedMap(new LinkedHashMap<>());
        while (resultSet.next()) {
            collection.put(resultSet.getInt("ID"), toLabWork(resultSet));
        }
        resultSet.close();
        return collection;
    }

    public LabWork toLabWork(ResultSet resultSet) throws SQLException {
        LabWork labWork = new LabWork();
        labWork.setId(resultSet.getLong("ID"));
        labWork.setName(resultSet.getString("Lab_Name"));
        labWork.setCoordinates(
                new Coordinates(resultSet.getLong("Coord_X"), resultSet.getDouble("Coord_Y")));
        labWork.setDate(resultSet.getString("Creation_Date"));
        labWork.setMinimalPoint(resultSet.getLong("Minimal_Point"));
        if (resultSet.getString("Tuned_In_Works") != null) {
            labWork.setTunedInWorks(resultSet.getLong("Tuned_In_Works"));
        }
        if (resultSet.getString("Average_Point") != null) {
            labWork.setAveragePoint(resultSet.getInt("Average_Point"));
        }
        if (resultSet.getString("Difficulty") != null) {
            labWork.setDifficulty(Difficulty.valueOf(resultSet.getString("Difficulty").toUpperCase()));
        }
        if (resultSet.getString("Author_Name") != null) {
            Person author = new Person();
            author.setName(resultSet.getString("Author_Name"));
            author.setWeight(resultSet.getFloat("Author_Weight"));
            if (resultSet.getString("Local_X") != null) {
                Location location = new Location(resultSet.getInt("Local_X"),
                        resultSet.getFloat("Local_Y"), resultSet.getDouble("Local_Z"));
                if (resultSet.getString("Local_Name") != null) {
                    location.setName(resultSet.getString("Local_Name"));
                }
                author.setLocation(location);
            }
            labWork.setAuthor(author);
        }
        return labWork;
    }

    public void addLabWork(String str, LabWork labWork) throws SQLException {
        String[] arg = str.split(":");
        String query = "INSERT INTO LabWork (ID, login, lab_name, coord_x, coord_y, creation_date, minimal_point)" +
                "VALUES (" + arg[1] + ", '" + arg[0] + "', '" + labWork.getName() + "', " + labWork.getCoordinates().getX() +
                ", " + labWork.getCoordinates().getY() + ", '" + labWork.getCreationDate().
                        format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "', " + labWork.getMinimalPoint() + ")";
        statement.addBatch(query);
        if (labWork.getTunedInWorks() != null) { fast("Tuned_In_Works", String.valueOf(labWork.getTunedInWorks()), arg[1]); }
        if (labWork.getAveragePoint() != null) { fast("Average_Point", String.valueOf(labWork.getAveragePoint()), arg[1]); }
        if (labWork.getDifficulty() != null) { fast("Difficulty", String.valueOf("'" + labWork.getDifficulty()) + "'", arg[1]); }
        if (labWork.getAuthor() != null) {
            fast("Author_Name", "'" + labWork.getAuthor().getName() + "'", arg[1]);
            fast("Author_Weight", String.valueOf(labWork.getAuthor().getWeight()), arg[1]);
            if (labWork.getAuthor().getLocation() != null) {
                fast("Local_X", String.valueOf(labWork.getAuthor().getLocation().getX()), arg[1]);
                fast("Local_Y", String.valueOf(labWork.getAuthor().getLocation().getY()), arg[1]);
                fast("Local_Z", String.valueOf(labWork.getAuthor().getLocation().getZ()), arg[1]);
                if (labWork.getAuthor().getLocation().getName() != null) {
                    fast("Local_Name", "'" + labWork.getAuthor().getLocation().getName() + "'", arg[1]);
                }
            }
        }
        statement.executeBatch();
    }

    public void fast(String column, String value, String id) throws SQLException {
        String query = "UPDATE LabWork SET " + column + " = " + value + " WHERE ID = " + id;
        statement.addBatch(query);
    }

    public boolean updateLabWork(String str, LabWork labWork) throws SQLException {
        String[] arg = str.split(":");
        if (validOwner(Long.valueOf(arg[1]), arg[0])) {
            String query = "DELETE FROM LabWork WHERE ID = " + arg[1];
            statement.addBatch(query);
            addLabWork(str, labWork);
            return true;
        }
        return false;
    }

    public boolean clearLab(String str) throws SQLException {
        String[] arg = str.split(":");
        if (validOwner(Long.valueOf(arg[1]), arg[0])) {
            String query = "DELETE FROM LabWork WHERE ID = " + arg[1];
            statement.execute(query);
            return true;
        }
        return false;
    }
}
