package commands;

import labWork.LabWork;
import server.ServerReader;

public class RemoveLowerCommand extends Command {

    public RemoveLowerCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute(LabWork labWork) {
        getCollection().values().removeIf(s -> s.compareTo(labWork) < 0);
        return ("Removed lower than given element");
    }
}
