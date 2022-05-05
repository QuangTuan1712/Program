package commands;

import server.ServerReader;

public class GroupByAverageCommand extends Command {

    public GroupByAverageCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute() {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        for (String key : getCollection().keySet()) {
            int point = 0;
            try {
                point = getCollection().get(key).getAveragePoint();
            } catch (NullPointerException e) {
            }
            if ( point < 10) a++;
            else if (point < 100) b++;
            else if (point < 1000) c++;
            else d++;
        }
        return ("2: " + a + "\n  3: " + b + "\n  4: " + c + "\n  5: " + d);
    }
}
