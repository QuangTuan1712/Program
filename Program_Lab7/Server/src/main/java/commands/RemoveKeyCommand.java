package commands;

import server.ServerReader;

import java.sql.SQLException;

public class RemoveKeyCommand extends Command {

    public RemoveKeyCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute(String str) throws SQLException {
        String[] arg = str.split(":");
        if (getCollection().containsKey(Integer.valueOf(arg[1]))) {
            if (serverReader.getBase().clearLab(str)) {
                serverReader.load();
                return ("Removed element with key " + arg[1]);
            } else {
                return "No access rights to the element with key " + arg[1];
            }
        }
        return ("No such element with key " + arg[1]);
    }
}
