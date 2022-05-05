package commands;

import labWork.LabWork;
import server.ServerReader;

import java.util.Objects;
import java.util.Set;

public class UpdateCommand extends Command {

    public UpdateCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute(String id, LabWork labWork) {
        Set<String> keySet = getCollection().keySet();
        for (String key : keySet) {
            if (Objects.equals(getCollection().get(key).getId(), Long.valueOf(id))) {
                labWork.setId(Long.valueOf(id));
                getCollection().put(key, labWork);
                return ("Updated element with id " + id);
            }
        }
        return ("No such element with id " + id);
    }
}
