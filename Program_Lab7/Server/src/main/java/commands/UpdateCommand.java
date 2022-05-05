package commands;

import labWork.LabWork;
import server.ServerReader;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Set;

public class UpdateCommand extends Command {

    public UpdateCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute(String str, LabWork labWork) throws SQLException {
        String[] arg = str.split(":");
/*        Set<Integer> keySet = getCollection().keySet();
        for (Integer key : keySet) {
            if (Objects.equals(getCollection().get(key).getId(), Long.valueOf(arg[1]))) {
                labWork.setId(Long.valueOf(arg[1]));
                getCollection().put(key, labWork);
                return ("Updated element with id " + arg[1]);
            }
        }*/
        if (getCollection().containsKey(Integer.valueOf(arg[1]))) {
            if (serverReader.getBase().updateLabWork(str, labWork)) {
                serverReader.load();
                return ("Updated element with id " + arg[1]);
            }
            return ("No access rights to the element with id " + arg[1]);
        }
        return "No such element with key " + arg[1];
    }
}
