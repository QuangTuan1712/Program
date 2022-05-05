package commands;

import dataBase.DataBaseCommunicator;
import server.ServerReader;

import java.sql.SQLException;

public class LoginCommand extends Command {
    public LoginCommand(ServerReader serverReader) { super(serverReader); }

    @Override
    public synchronized String execute(String str) {
        String[] user = str.split(":", 2);
        try {
            if (DataBaseCommunicator.getUsers().validation(user[0], user[1])) {
                return "OK";
            } else {
                return "Wrong login or password! Please try again";
            }
        } catch (SQLException e) {
            System.out.println("\n  Error with database");
        }
        return "Error";
    }
}
