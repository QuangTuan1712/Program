package t1u7a1n2.command;

import t1u7a1n2.CollectionManager;

public class UpdateCommand extends Command {
    private CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String id) {
        collectionManager.update(Long.parseLong(id));
    }
}
