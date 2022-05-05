package utils;

import labWork.*;

import java.io.Serializable;

public class NoName implements Serializable {
    private static final long serialVersionUID = 1L;
    String command;
    LabWork labWork;
    User user;

    public NoName(String command) {
        this.command = command;
    }

    public NoName(String command, LabWork labWork) {
        this.command = command;
        this.labWork = labWork;
    }

    public NoName(String command, User user) {
        this.command = command;
        this.user = user;
    }
    
    public NoName(String command, LabWork labWork, User user) {
        this.command = command;
        this.labWork = labWork;
        this.user = user;
    }

    public void setString(String command) {
        this.command = command;
    }

    public String getString() {
        return command;
    }

    public void setLabWork(LabWork labWork) {
        this.labWork = labWork;
    }

    public LabWork getLabWork() {
        return labWork;
    }

    public void setUser(User user) { this.user = user; }

    public User getUser() { return user; }
}
