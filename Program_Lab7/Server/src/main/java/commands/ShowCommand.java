package commands;

import server.ServerReader;

public class ShowCommand extends Command {

    public ShowCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute() {
        if (getCollection().isEmpty()) { return ("\n  Collection is empty."); }
        else {
            StringBuilder answer = new StringBuilder();
            int i = getCollection().size();
            for (Integer key : getCollection().keySet()) {
                answer.append(getCollection().get(key).display());
                if( i > 1) { answer.append("\n\n"); i--; }
                else { answer.append("\n"); }
            }
            return answer.toString();
        }
    }
}
