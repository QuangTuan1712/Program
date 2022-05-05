package server;

import utils.NoName;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.*;

public class ServerRun extends Thread{
    DatagramChannel server;
    ByteBuffer buffer;
    private static final int valueOfBuffer = 65536;
    ServerCommandReader commandReader;
    Logger logger;

    public ServerRun(ServerCommandReader serverCommandReader) {
        commandReader = serverCommandReader;
    }

    @Override
    public void start() {
        try {
            server = DatagramChannel.open();
            InetSocketAddress iAdd = new InetSocketAddress("localhost", 8989);
            server.bind(iAdd);
            System.out.println("--------------------------------------\n\nServer started: #" + iAdd);
            buffer = ByteBuffer.allocate(valueOfBuffer);
            logger = Logger.getLogger("Server");
            logger.info("Server start: #" + iAdd + ".\n");

            while (true) {
                SocketAddress remoteAdd = server.receive(buffer);
                NoName msg = extractMessage(buffer);
                System.out.println("Client at " + remoteAdd + "  sent: " + msg.getString());
                logger.info("Received  [ " + msg.getString() + " ]  from " + remoteAdd + ".\n");

                String[] news = msg.getString().split(";");
                String message;
                if (news[0].equals("save")) {
                    message = "Command 'save' not found. Use help to display commands in program\n";
                } else {
                    message = commandReader.executeCommand(msg);
                }
                sendMessage(server, message, remoteAdd);
                logger.info("Send to " + remoteAdd + "  [ " + message.trim() + " ].\n");
            }
        } catch (IOException e) {
            System.err.println("\n  Server down.");
            logger.warning("Server down");
            System.exit(-1);
        } catch (ClassNotFoundException ignored) {
        }
    }

    public byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }

    public NoName deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream b = new ByteArrayInputStream(bytes)) {
            try (ObjectInputStream o = new ObjectInputStream(b)) {
                return (NoName) o.readObject();
            }
        }
    }

    public NoName extractMessage(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        buffer.flip();
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes, 0, buffer.limit());
        NoName msg = deserialize(bytes);
        buffer.clear();
        return msg;
    }

    public void sendMessage(DatagramChannel server, String msg, SocketAddress clientAdd) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(serialize(new NoName(msg)));
        server.send(buffer, clientAdd);
        buffer.clear();
    }
}
