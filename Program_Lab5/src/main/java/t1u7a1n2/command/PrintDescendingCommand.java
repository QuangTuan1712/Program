package t1u7a1n2.command;

import t1u7a1n2.CollectionManager;

public class PrintDescendingCommand extends Command {
    private CollectionManager collectionManager;

    public PrintDescendingCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        collectionManager.print_descending();
    }
}
