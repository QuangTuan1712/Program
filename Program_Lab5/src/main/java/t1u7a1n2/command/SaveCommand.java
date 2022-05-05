package t1u7a1n2.command;

import t1u7a1n2.CollectionManager;

public class SaveCommand extends Command {
    private CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public  void execute(String file) {
        collectionManager.save(file);
    }
}
