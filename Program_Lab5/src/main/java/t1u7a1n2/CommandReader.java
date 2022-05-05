package t1u7a1n2;

import t1u7a1n2.command.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandReader {
    private int num = 1;
    private String[] userCommand = new String[14];
    private String[] cm_splited = new String[2];
    HashSet<String> loadedScript = new HashSet<>();

    {
        userCommand[0] = "";
    }

    private ClearCommand clearCommand;
    private GroupByAverageCommand groupByAverageCommand;
    private HelpCommand helpCommand;
    private InfoCommand infoCommand;
    private InsertCommand insertCommand;
    private MaxByIdCommand maxByIdCommand;
    private PrintDescendingCommand printDescendingCommand;
    private RemoveKeyCommand removeKeyCommand;
    private RemoveLowerCommand removeLowerCommand;
    private ReplaceIfGreaterCommand replaceIfGreaterCommand;
    private SaveCommand saveCommand;
    private ShowCommand showCommand;
    private UpdateCommand updateCommand;

    public CommandReader(){}
    public CommandReader(CollectionManager collectionManager) {
        this.clearCommand = new ClearCommand(collectionManager);
        this.groupByAverageCommand = new GroupByAverageCommand(collectionManager);
        this.helpCommand = new HelpCommand(collectionManager);
        this.infoCommand = new InfoCommand(collectionManager);
        this.insertCommand = new InsertCommand(collectionManager);
        this.maxByIdCommand = new MaxByIdCommand(collectionManager);
        this.printDescendingCommand = new PrintDescendingCommand(collectionManager);
        this.removeKeyCommand = new RemoveKeyCommand(collectionManager);
        this.removeLowerCommand = new RemoveLowerCommand(collectionManager);
        this.replaceIfGreaterCommand = new ReplaceIfGreaterCommand(collectionManager);
        this.saveCommand = new SaveCommand(collectionManager);
        this.showCommand = new ShowCommand(collectionManager);
        this.updateCommand = new UpdateCommand(collectionManager);

    }

    /**
     * Distribute command to corresponding method
     * @param s command as string typed by user
     */
    public void executeCommand(String s) {
        //cm_splited[0] is command, cm_splited[1] is argument
        boolean v = true;
        cm_splited = s.trim().split(" ", 2);
        try{
            switch (cm_splited[0]) {
                case "help": helpCommand.execute(); break;
                case "info": infoCommand.execute(); break;
                case "show": showCommand.execute();break;
                case "insert": insertCommand.execute(cm_splited[1]); break;
                case "update": updateCommand.execute(cm_splited[1]); break;
                case "remove_key": removeKeyCommand.execute(cm_splited[1]); break;
                case "clear": clearCommand.execute(); break;
                case "save": saveCommand.execute("Output.xml"); break;
                case "execute_script": execute_script(cm_splited[1]); break;
                case "exit": System.exit(1); break;
                case "remove_lower": removeLowerCommand.execute(); break;
                case "history": displayHistory(); break;
                case "replace_if_greater": replaceIfGreaterCommand.execute(cm_splited[1]); break;
                case "max_by_id": maxByIdCommand.execute(); break;
                case "group_counting_by_average_point": groupByAverageCommand.execute(); break;
                case "print_descending": printDescendingCommand.execute(); break;
                default: v = false; System.out.println("\n  Command '" + cm_splited[0] + "' not found. Use help to display commands in program"); break;
            }
            if (v) {
                userCommand = appendArray(userCommand, cm_splited[0]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\n  Argument missing.");
        }
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
        for (int i=1; i<14; i++) {
            newArray[i] = oldArray[i-1];
        }
        return newArray;
    }

    /**
     * Scan input from user and parse to executeCommand()
     */
    public void readCommand() {
        Scanner commandReader = new Scanner(System.in);
        try {
            while (!userCommand[0].equals("exit")) {
                System.out.print("\n$ ");
                executeCommand(commandReader.nextLine());
            }
        } catch (NoSuchElementException ex) {
            System.exit(0);
        }
    }

    /**
     * Import script file and execute it
     * @param file source file
     */
    private void execute_script(String file) {
        if (!loadedScript.contains(file)) {
            loadedScript.add(file);
            try {
                Scanner sc = new Scanner(new FileInputStream(file));
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    System.out.print("\n" + num + ". " + line + "\n");
                    if (!line.isEmpty()) executeCommand(line);
                    num++;
                }
                System.out.println("\n  Script completed!");
            } catch (FileNotFoundException e) {
                System.out.println("  File not found.");
            }
            loadedScript.remove(file);
        } else {
            System.out.println("\n  Script is already exist");
        }
    }

    /**
     * Display history
     */
    private void displayHistory() {
        int n = 14;
        System.out.println();
        for (int i = 0; i < n; i++) {
            if (userCommand[n-1-i] != null && userCommand[n-1-i] != "") {
                System.out.println("  " + userCommand[n-1-i]);
            }
        }
    }
}
