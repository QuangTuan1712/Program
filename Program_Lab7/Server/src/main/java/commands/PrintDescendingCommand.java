package commands;

import labWork.LabWork;
import server.ServerReader;

import java.util.LinkedHashMap;

public class PrintDescendingCommand extends Command {

    public PrintDescendingCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute() {
        LinkedHashMap<Integer, LabWork> newColl = new LinkedHashMap<>(getCollection());
        String msg = "";
        if (newColl.isEmpty()) {
            msg += ("Collection is empty");
        } else {
            while (!newColl.isEmpty()) {
                msg = this.max_by_average(newColl, msg);
            }
        }
        return msg;
    }

    /**
     * Display element with maximum Average Point
     */
    private String max_by_average(LinkedHashMap<Integer, LabWork> collection, String msg) {
        int keys = 0;
        int ids = 0;
        for (Integer key : collection.keySet()) {
            if (collection.get(key).getAveragePoint() != null && collection.get(key).getAveragePoint() > ids) { ids = collection.get(key).getAveragePoint(); keys = key; }
            else {
                if (collection.get(key).getAveragePoint() == null && ids == 0) { keys = key; } }
        }
        msg += collection.get(keys).display();
        collection.remove(keys);
        if (!collection.isEmpty()) msg += ("\n\n");
        else { msg += "\n"; }
        return msg;
    }
}
