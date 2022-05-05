package server;

import commands.*;
import utils.NoName;


public class ServerCommandReader{
    private String[] history = new String[14];

    private final ClearCommand clearCommand;
    private final GroupByAverageCommand groupByAverageCommand;
    private final HelpCommand helpCommand;
    private final InfoCommand infoCommand;
    private final InsertCommand insertCommand;
    private final MaxByIdCommand maxByIdCommand;
    private final PrintDescendingCommand printDescendingCommand;
    private final RemoveKeyCommand removeKeyCommand;
    private final RemoveLowerCommand removeLowerCommand;
    private final ReplaceIfGreaterCommand replaceIfGreaterCommand;
    private final SaveCommand saveCommand;
    private final ShowCommand showCommand;
    private final UpdateCommand updateCommand;

    public ServerCommandReader(ServerReader reader) {
        history[0] = "";
        this.clearCommand = new ClearCommand(reader);
        this.groupByAverageCommand = new GroupByAverageCommand(reader);
        this.helpCommand = new HelpCommand(reader);
        this.infoCommand = new InfoCommand(reader);
        this.insertCommand = new InsertCommand(reader);
        this.maxByIdCommand = new MaxByIdCommand(reader);
        this.printDescendingCommand = new PrintDescendingCommand(reader);
        this.removeKeyCommand = new RemoveKeyCommand(reader);
        this.removeLowerCommand = new RemoveLowerCommand(reader);
        this.replaceIfGreaterCommand = new ReplaceIfGreaterCommand(reader);
        this.saveCommand = new SaveCommand(reader);
        this.showCommand = new ShowCommand(reader);
        this.updateCommand = new UpdateCommand(reader);
    }

    public String executeCommand(NoName s) {
        boolean v = true;
        String[] msg = s.getString().split(" ", 2);
        String answer = "";
        try{
            switch (msg[0]) {
                case "help": answer = helpCommand.execute(); break;
                case "info": answer = infoCommand.execute(); break;
                case "show": answer = showCommand.execute();break;
                case "insert": answer = insertCommand.execute(msg[1], s.getLabWork()); break;
                case "update": answer = updateCommand.execute(msg[1], s.getLabWork()); break;
                case "remove_key": answer = removeKeyCommand.execute(msg[1]); break;
                case "clear": answer = clearCommand.execute(); break;
                case "save": answer = saveCommand.execute("LabWork.xml"); break;
                case "exit": answer = "Server closed ..."; executeCommand(new NoName("save")); break;
                case "remove_lower": answer = removeLowerCommand.execute(s.getLabWork()); break;
                case "history": answer = displayHistory(); break;
                case "replace_if_greater": answer = replaceIfGreaterCommand.execute(msg[1], s.getLabWork()); break;
                case "max_by_id": answer = maxByIdCommand.execute(); break;
                case "group_counting_by_average_point": answer = groupByAverageCommand.execute(); break;
                case "print_descending": answer = printDescendingCommand.execute(); break;
//                case "test": answer = "This is test"; break;
                default: v = false; answer = ("Command '" + msg[0] + "' not found. Use help to display commands in program\n"); break;
            }
            if (v) {
                history = appendArray(history, msg[0]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            answer = "Argument missing.";
        }
        return answer;
    }

    /**
     * Safe way to append new element into array (outofbounds exception)
     * @param oldArray array to execute
     * @param value element to add
     * @return new array
     */
    private String[] appendArray(String[] oldArray, String value) {
        String[] newArray = new String[14];
        newArray[0] = value;
        System.arraycopy(oldArray, 0, newArray, 1, 13);
        return newArray;
    }

    /**
     * Display history
     */
    private String displayHistory() {
        int n = 14;
        StringBuilder msg = new StringBuilder();
        msg.append("\n");
        for (int i = 0; i < n; i++) {
            if (history[n-1-i] != null) {
                msg.append("  ").append(history[n - 1 - i]).append("\n");
            }
        }
        return msg.toString();
    }
}
