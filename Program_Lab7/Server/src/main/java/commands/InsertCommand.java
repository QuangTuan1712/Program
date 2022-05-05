package commands;

import dataBase.LabWorkBase;
import labWork.LabWork;
import server.ServerReader;

import java.sql.SQLException;

public class InsertCommand extends Command {

    public InsertCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute(String str, LabWork labWork) throws SQLException {
        String[] key = str.split(":");
        if (getCollection().containsKey(Integer.valueOf(key[1]))) { return ("Key " + key[1] + " is exist");
        } else {
            serverReader.getBase().addLabWork(str, labWork);
            serverReader.load();
            return ("Added new element \"" + labWork.getName() + "\" successfully with key " + key[1]);
        }
    }
}
