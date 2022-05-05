package commands;

import server.ServerReader;

import java.sql.SQLException;

public class ClearCommand extends Command {

    public ClearCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute(String login) throws SQLException {
        serverReader.getBase().clearBase(login);
        serverReader.load();
        return ("Collection cleared");
    }
}
