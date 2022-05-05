package t1u7a1n2.command;

import t1u7a1n2.CollectionManager;

public class GroupByAverageCommand extends Command {
    private CollectionManager collectionManager;

    public GroupByAverageCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        collectionManager.group_counting_by_average_point();
    }
}
