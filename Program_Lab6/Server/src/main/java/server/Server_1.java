package server;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Server_1 {
    private static int valueOfBuffer = 65536;
    ServerCommandReader commandReader;

    public Server_1(ServerCommandReader serverCommandReader) {
        commandReader = serverCommandReader;
    }

    public void start() throws IOException {
        try {
            DatagramChannel server = DatagramChannel.open();
            InetSocketAddress iAdd = new InetSocketAddress("localhost", 8989);
            server.bind(iAdd);
            System.out.println("Server Started: #" + iAdd);
            ByteBuffer buffer = ByteBuffer.allocate(valueOfBuffer);
            while (true) {
                SocketAddress remoteAdd = server.receive(buffer);
/*                buffer.flip();
                int limits = buffer.limit();
                byte[] bytes = new byte[limits];
                buffer.get(bytes, 0, limits);
                Exchanger exchanger = deserialize(bytes);
                System.out.println("Client at " + remoteAdd + "  sent: " + exchanger.getCommand() + " " + exchanger.getArgument());
                exchanger = commandReader.executeCommand(exchanger);

                buffer = ByteBuffer.wrap(serialize(exchanger));
                server.send(buffer, remoteAdd);
 */
                String msg = extractMessage(buffer);
                System.out.println("Client at " + remoteAdd + "  sent: " + msg);

                String message = msg + " from Server";
                sendMessage(server, message, remoteAdd);
            }
        } catch (IOException e) {
            System.out.println("Server down.");
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
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

    public String deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream b = new ByteArrayInputStream(bytes)) {
            try (ObjectInputStream o = new ObjectInputStream(b)) {
                return (String) o.readObject();
            }
        }
    }

    public String extractMessage(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        buffer.flip();
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes, 0, buffer.limit());
        String msg = deserialize(bytes);
        buffer.clear();
        return msg;
    }

    public void sendMessage(DatagramChannel server, String msg, SocketAddress clientAdd) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(serialize(msg));
        server.send(buffer, clientAdd);
        buffer.clear();
    }
}
