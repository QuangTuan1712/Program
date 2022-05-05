package commands;

import labWork.LabWork;
import server.ServerReader;

import java.sql.SQLException;

public class RemoveLowerCommand extends Command {

    public RemoveLowerCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute(String login, LabWork labWork) throws SQLException {
        for (Integer key: getCollection().keySet()) {
            if (getCollection().get(key).compareTo(labWork) < 0) {
                serverReader.getBase().clearLab(login + ":" + key);
            }
        }
        serverReader.load();
        return ("Removed lower than given element");
    }
}
