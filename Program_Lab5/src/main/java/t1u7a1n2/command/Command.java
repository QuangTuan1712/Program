package t1u7a1n2.command;

import t1u7a1n2.labwork.LabWork;

/**
 * Parent of all commands classes
 */
public abstract class Command {

    public void execute(){
    }

    public void execute(String argument) {
    }

    public  void execute(LabWork labWork) {
    }

    public void execute(String argument, LabWork labWork) {
    }
}
