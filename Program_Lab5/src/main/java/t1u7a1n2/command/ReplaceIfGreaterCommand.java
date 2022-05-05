package t1u7a1n2.command;

import t1u7a1n2.CollectionManager;

public class ReplaceIfGreaterCommand extends Command {
    private CollectionManager collectionManager;

    public ReplaceIfGreaterCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String key) {
        collectionManager.replace_if_greater(key);
    }
}
