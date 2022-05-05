package t1u7a1n2.command;

import t1u7a1n2.CollectionManager;

public class HelpCommand extends Command {
    private CollectionManager collectionManager;
    public HelpCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        collectionManager.help();
    }
}
