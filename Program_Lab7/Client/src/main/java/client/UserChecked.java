package client;

import utils.NoName;
import utils.User;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class UserChecked {
    private DatagramSocket clientSocket;
    private InetAddress IPAdress;
    private final int PORT;
    private UserAsker userAsker = new UserAsker();
    private Scanner sc = new Scanner(System.in);
    private int check = 1;

    public UserChecked(DatagramSocket clientSocket, InetAddress IPAdress, int port) {
        this.clientSocket = clientSocket;
        this.IPAdress = IPAdress;
        this.PORT = port;
    }

    public User authorization() throws IOException {
        while (true) {
            byte[] sendingDataBuffer;// = new byte[8192];
            byte[] receivingDataBuffer = new byte[8192];

            System.out.print("\n  Already have an account? (+/-)  >  ");
            String str = sc.nextLine().trim();
            if (!str.equals("+") && !str.equals("-") && !str.equals("*")) {
                if (check == 5) {
                    System.out.println("\n  Connection attempts exceeded!!!  (╯°□°）╯︵ ┻━┻");
                    System.exit(0);
                }
                System.out.print("  Enter '+' or '-'!   >  ");
                check++;
            } else {
                User user = userAsker.askClient(str);
                str = (str.equals("+") || str.equals("*")) ? "#login*" : "#register*";

                String test = "";
                try {
                    sendingDataBuffer = serialize(new NoName(str, user));
                    DatagramPacket sending = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, IPAdress, PORT);
                    clientSocket.send(sending);

                    DatagramPacket receiving = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
                    clientSocket.receive(receiving);

                    test = deserialize(receiving.getData()).getString();
                } catch (ClassNotFoundException ignored) {
                } catch (SocketTimeoutException e) {
                    System.out.println("\n  No connection! Please try again later.");
                    System.exit(0);
                }
                if (test.equals("OK")) {
                    return user;
                } else {
                    System.out.println("  " + test);
                }
            }
        }
    }

    public static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }

    public static NoName deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream b = new ByteArrayInputStream(bytes)) {
            try (ObjectInputStream o = new ObjectInputStream(b)) {
                return (NoName) o.readObject();
            }
        }
    }
}
