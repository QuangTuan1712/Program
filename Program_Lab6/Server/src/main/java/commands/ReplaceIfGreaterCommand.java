package commands;

import labWork.LabWork;
import server.ServerReader;

public class ReplaceIfGreaterCommand extends Command {

    public ReplaceIfGreaterCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute(String key, LabWork labWork) {
        if (getCollection().containsKey(key)) {
            if (labWork.compareTo(getCollection().get(key)) > 0) {
                getCollection().put(key, labWork);
                return ("Replaced element with key " + key);
            } else {
                return ("New element is less than old");
            }
        }
        return ("No such element with key " + key);
    }
}
