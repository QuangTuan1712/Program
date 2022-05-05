package commands;

import server.ServerReader;

public class MaxByIdCommand extends Command {

    public MaxByIdCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute() {
        if (!getCollection().isEmpty()) {
            int keys = 0;
            long ids = 0;
            for (Integer key : getCollection().keySet()) {
                if (getCollection().get(key).getId() > ids) {
                    ids = getCollection().get(key).getId();
                    keys = key;
                }
            }
            return getCollection().get(keys).display();
        }
        return "Collection is empty.";
    }
}
