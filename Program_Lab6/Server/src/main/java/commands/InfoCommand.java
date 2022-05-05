package commands;

import server.ServerReader;

public class InfoCommand extends Command {

    public InfoCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute() {
        return ("Type:  LinkedHashMap\n" +
                "  Data of initialization:  " + getServerReader().timeStamp +
                "\n  Size:  " + getCollection().size());
    }
}
