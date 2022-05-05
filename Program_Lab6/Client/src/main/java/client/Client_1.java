package client;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class Client_1 {
    public final static int SERVICE_PORT = 8989;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("  Connecting ...");
        Thread.sleep(400);
        DatagramSocket clientSocket = new DatagramSocket();
        clientSocket.setSoTimeout(3000);

        InetAddress IPAddress = InetAddress.getByName("localhost");

        while (true) {
            byte[] sendingDataBuffer = new byte[1024];
            byte[] receivingDataBuffer = new byte[1024];

            try {
                System.out.print("Client: ");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String sentence = br.readLine();

                if (sentence.compareTo("exit") == 0) {
                    break;
                }
                if (sentence.compareTo("") != 0) {
//                    sendingDataBuffer = sentence.getBytes();
                    String letter = "From Client: " + sentence;
                    sendingDataBuffer = serialize(letter);
                    DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, IPAddress, SERVICE_PORT);

                    clientSocket.send(sendingPacket);

                    DatagramPacket receivingPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);

//                    Thread.sleep(100);
                    clientSocket.receive(receivingPacket);

//                    String receivedData = new String(receivingPacket.getData());
                    String receivedData = deserialize(receivingPacket.getData());
                    System.out.println("Sent from Server: " + receivedData.trim());
                }
            } catch (SocketTimeoutException | ClassNotFoundException e) {
                System.out.println("Timed out! Reconnect ...");
                Thread.sleep(300);
                continue;
            }
        }
        clientSocket.close();
    }

    public static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }

    public static String deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream b = new ByteArrayInputStream(bytes)) {
            try (ObjectInputStream o = new ObjectInputStream(b)) {
                return (String) o.readObject();
            }
        }
    }
}
