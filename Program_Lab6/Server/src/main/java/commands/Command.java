package commands;

import labWork.*;
import server.ServerReader;

import java.util.LinkedHashMap;

/**
 * Parent of all commands classes
 */
public abstract class Command {
    public ServerReader serverReader;

    public Command(ServerReader serverReader) {
        this.serverReader = serverReader;
    }

    public synchronized String execute(){
        return execute();
    }

    public synchronized String execute(String file) {
        return execute();
    }

    public synchronized String execute(LabWork labWork) {
        return execute();
    }

    public synchronized String execute(String str, LabWork labWork) { return execute(); }

    public void setServerReader(ServerReader serverReader) {
        this.serverReader = serverReader;
    }

    public ServerReader getServerReader() {
        return serverReader;
    }

    public LinkedHashMap<String, LabWork> getCollection() {
        return serverReader.getCollection();
    }
}
