package t1u7a1n2.command;

import t1u7a1n2.CollectionManager;

public class ShowCommand extends Command {
    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        collectionManager.show();
    }
}
