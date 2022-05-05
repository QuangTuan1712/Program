package t1u7a1n2.command;

import t1u7a1n2.CollectionManager;

public class ClearCommand extends Command {
    private CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {

        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        collectionManager.clear();
    }
}
