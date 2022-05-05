package commands;

import server.ServerReader;

public class HelpCommand extends Command {
    public HelpCommand(ServerReader serverReader) {
        super(serverReader);
    }

    @Override
    public synchronized String execute() {
        return("help:     display help for available commands\n" +
                "  info:     print information about the collection to standard output\n" +
                "  show:     display all elements of the collection in string representation to standard output\n" +
                "  insert null {element}:     add a new element with the given key\n" +
                "  update id {element}:     update the value of the collection element whose id is equal to the given\n" +
                "  remove_key null:     remove an item from the collection by its key \n" +
                "  clear:     clear the collection\n" +
                "  exit:     exit the program\n" +
                "  remove_lower {element}:     remove all elements from the collection that are less than the given one\n" +
                "  history:     display the last 14 commands (without their arguments)\n" +
                "  replace_if_greater null {element}:     replace value by key if new value is greater than old\n" +
                "  max_by_id:     display any object from the collection, the value of the id field of which is the maximum\n" +
                "  group_counting_by_average_point:     group the elements of the collection by the value of the averagePoint field, display the number of elements in each group\n" +
                "  print_descending:     display the elements of the collection in descending order");
    }
}
