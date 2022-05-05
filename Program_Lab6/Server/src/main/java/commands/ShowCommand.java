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
            String answer = "";
            int i = getCollection().size();
            for (String key : getCollection().keySet()) {
                answer += getCollection().get(key).display();
                if( i > 1) { answer += ("\n\n"); i--; }
                else { answer += "\n"; }
            }
            return answer;
        }
    }
}
