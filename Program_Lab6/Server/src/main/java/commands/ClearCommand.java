package commands;

import server.ServerReader;

public class ClearCommand extends Command {

    public ClearCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute() {
        getCollection().clear();
        return ("Collection cleared");
    }
}
