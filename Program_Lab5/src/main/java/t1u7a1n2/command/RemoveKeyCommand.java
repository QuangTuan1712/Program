package t1u7a1n2.command;

import t1u7a1n2.CollectionManager;

public class RemoveKeyCommand extends Command {
    private CollectionManager collectionManager;

    public RemoveKeyCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String key) {
        collectionManager.remove_key(key);
    }
}
