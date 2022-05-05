package commands;

import labWork.LabWork;
import server.ServerReader;

public class InsertCommand extends Command {

    public InsertCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute(String key, LabWork labWork) {
        if (getCollection().containsKey(key)) { return ("Key " + key + " is exist");
        } else {
            getCollection().put(key, labWork);
            return ("Added new element " + labWork.getName() + " successfully with key " + key);
        }
    }
}
