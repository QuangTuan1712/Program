package t1u7a1n2.command;

import t1u7a1n2.CollectionManager;

public class InsertCommand extends Command {
    private CollectionManager collectionManager;

    public InsertCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) {
        collectionManager.insert(argument);
    }
}
