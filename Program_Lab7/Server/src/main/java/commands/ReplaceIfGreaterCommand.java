package commands;

import labWork.LabWork;
import server.ServerReader;

import java.sql.SQLException;

public class ReplaceIfGreaterCommand extends Command {

    public ReplaceIfGreaterCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute(String str, LabWork labWork) throws SQLException {
        String[] arg = str.split(":");
        if (getCollection().containsKey(Integer.valueOf(arg[1]))) {
            if (labWork.compareTo(getCollection().get(Integer.valueOf(arg[1]))) > 0) {
                System.out.println(str);
                if (serverReader.getBase().updateLabWork(str, labWork)) {
                    serverReader.load();
                    return ("Replaced element with key " + arg[1]);
                } else { return "No access rights to the element with key " + arg[1]; }
            } else {
                return ("New element is less than old");
            }
        }
        return ("No such element with key " + arg[1]);
    }
}
