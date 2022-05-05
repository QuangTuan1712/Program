package server;

import utils.NoName;

import java.util.concurrent.RecursiveTask;

public class RequestTask extends RecursiveTask<String> {
    private ServerCommandReader commandReader;
    private NoName noName;

    public RequestTask(ServerCommandReader commandReader, NoName noName) {
        this.commandReader = commandReader;
        this.noName = noName;
    }

    @Override
    protected String compute() {
        return commandReader.executeCommand(noName);
    }
}
