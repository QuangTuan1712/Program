package t1u7a1n2.command;

import t1u7a1n2.CollectionManager;

public class RemoveLowerCommand extends Command {
    private CollectionManager collectionManager;

    public RemoveLowerCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        collectionManager.remove_lower();
    }
}
