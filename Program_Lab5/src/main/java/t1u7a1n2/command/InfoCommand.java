package t1u7a1n2.command;

import t1u7a1n2.CollectionManager;

public class InfoCommand extends Command {
    private CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        collectionManager.info();
    }
}
