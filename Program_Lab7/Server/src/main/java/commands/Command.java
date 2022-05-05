package commands;

import labWork.*;
import server.ServerReader;

import java.sql.SQLException;
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

    public synchronized String execute(String file) throws SQLException {
        return execute();
    }

    public synchronized String execute(LabWork labWork) throws SQLException {
        return execute();
    }

    public synchronized String execute(String key, LabWork labWork) throws SQLException { return execute(); }

    public void setServerReader(ServerReader serverReader) {
        this.serverReader = serverReader;
    }

    public ServerReader getServerReader() {
        return serverReader;
    }

    public LinkedHashMap<Integer, LabWork> getCollection() {
        return serverReader.getCollection();
    }
}
