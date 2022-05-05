package commands;

import server.ServerReader;

public class RemoveKeyCommand extends Command {

    public RemoveKeyCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute(String key) {
        getCollection().remove(key);
        return ("Removed element with key " + key);
    }
}
