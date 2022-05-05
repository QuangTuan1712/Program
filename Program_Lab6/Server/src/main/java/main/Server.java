package main;

import server.*;
import utils.MyLogFormatter;

import java.io.IOException;
import java.util.logging.*;

public class Server {
    public static Logger logger;

    public static void main(String[] args) {
        String file = "LabWork.xml";
        ServerReader reader = new ServerReader(file);
        ServerCommandReader commandReader = new ServerCommandReader(reader);
        ServerRun server = new ServerRun(commandReader);
        logger_start();
        Runnable r = new Console(commandReader, logger);
        Thread t = new Thread(r); t.start();
        server.start();
    }

    public static void logger_start() {
        logger = Logger.getLogger("Server");
        logger.setLevel(Level.INFO);
        FileHandler fileLog;
        try {
            fileLog = new FileHandler("Logging.txt", true);
            logger.setUseParentHandlers(false);
            logger.addHandler(fileLog);
            fileLog.setFormatter(new MyLogFormatter());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}
