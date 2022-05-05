package commands;

import dataBase.DataBaseCommunicator;
import server.ServerReader;
import utils.NoName;
import utils.User;

import java.sql.SQLException;

public class RegisterCommand extends Command {
    public RegisterCommand(ServerReader serverReader) { super(serverReader); }

    @Override
    public synchronized String execute(String str) {
        String[] user = str.split(":", 2);
        try {
            if (DataBaseCommunicator.getUsers().isExisting(user[0])) {
                return "User already exist.";
            } else {
                DataBaseCommunicator.getUsers().addUser(user[0], user[1]);
                return "OK";
            }
        } catch (SQLException e) {
            System.out.println("\n  Error with database");
            e.printStackTrace();
        }
        return "Error";
    }
}
