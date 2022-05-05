package t1u7a1n2.command;

import t1u7a1n2.CollectionManager;

public class MaxByIdCommand extends Command {
    private CollectionManager collectionManager;

    public MaxByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        collectionManager.max_by_id();
    }
}
