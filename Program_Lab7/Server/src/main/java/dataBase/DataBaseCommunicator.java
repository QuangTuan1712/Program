package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseCommunicator {
    private static  UserBase userBase;
    private static LabWorkBase labWorkBase;

    public void start() {
        try {
            Connection connection = DriverManager
//                    .getConnection("jdbc:postgresql://pg:5432/studs", "s317709","??????");
                    .getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
            userBase = new UserBase(connection);
            labWorkBase = new LabWorkBase(connection);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static UserBase getUsers() {
        return userBase;
    }

    public LabWorkBase getLabWorkBase() {
        return labWorkBase;
    }
}
