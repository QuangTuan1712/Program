package client;

import utils.NoName;
import labWork.*;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    public final static int SERVICE_PORT = 8989;

    public static void main(String[] args) throws IOException{
        System.out.println("--------------------------------------\n\nConnecting ...");
        DatagramSocket clientSocket = new DatagramSocket();
        clientSocket.setSoTimeout(2500);
        InetAddress IPAddress = InetAddress.getByName("localhost");

        while (true) {
            byte[] sendingDataBuffer;// = new byte[8192];
            byte[] receivingDataBuffer = new byte[8192];

            try {
                System.out.print("\nClient:$ ");
                String sentence = readCommand();

                if (sentence.equals("exit")) {
                    break;
                }
                if (!sentence.equals("")) {
                    String[] cm_split = sentence.trim().split(" ", 2);
                    switch (cm_split[0]) {
                        case "insert":
                        case "update":
                        case "remove_lower":
                        case "replace_if_greater": sendingDataBuffer = serialize(new NoName(sentence, createLab())); break;
                        default: sendingDataBuffer = serialize(new NoName(sentence));
                    }

                    DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, IPAddress, SERVICE_PORT);
                    clientSocket.send(sendingPacket);

                    DatagramPacket receivingPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
                    clientSocket.receive(receivingPacket);

                    String receivedData = deserialize(receivingPacket.getData()).getString();
                    System.out.println("\nSent from Server:\n  " + receivedData.trim());
                }
            } catch (SocketTimeoutException e) {
                System.out.println("\n  Timed out! Reconnect ...");
            } catch (ClassNotFoundException e) {
                System.err.println("\n  Can't send to Server! Reconnect ...");
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

    public static NoName deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream b = new ByteArrayInputStream(bytes)) {
            try (ObjectInputStream o = new ObjectInputStream(b)) {
                return (NoName) o.readObject();
            }
        }
    }

    public static String readCommand() {
        try {
            Scanner sc = new Scanner(System.in);
            return sc.nextLine();
        } catch (NoSuchElementException e) {
            System.out.print("\n");
            System.exit(0);
        }
        return "";
    }

    private static LabWork createLab() {
        LabWork labWork = new LabWork();
        try {
            BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
            boolean bool = true;
            while (labWork.getName() == null || labWork.getName().equals("")) {
                System.out.print("  Name (can't be empty): ");
                try {
                    labWork.setName(scanner.readLine());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            while (labWork.getCoordinates() == null) {
                System.out.print("  Coordinates (can't be empty, format \"x y\", x long, y double): ");
                String scan = scanner.readLine();
                if (scan.trim().split(" ").length == 1) {
                    System.out.println("  Must be 2 numbers");
                } else {
                    String[] sc = scan.trim().split(" ", 2);
                    try {
                        labWork.setCoordinates(new Coordinates(Long.valueOf(sc[0]), Double.valueOf(sc[1])));
                    } catch (NumberFormatException e) {
                        System.out.println("  Wrong coordinate format");
                    }
                }
            }
            while (labWork.getMinimalPoint() <= 0) {
                System.out.print("  Minimal Point (long, be larger than 0): ");
                try {
                    long sc = Long.parseLong(scanner.readLine().trim());
                    if (sc <= 0) {
                        System.out.println("  Be larger than 0!!!");
                    } else {
                        labWork.setMinimalPoint(sc);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("  Wrong number format");
                }

            }
            while (bool) {
                System.out.print("  Tuned In Works (long, can be null): ");
                String sc = scanner.readLine().trim();
                if (sc.equals("")) {
                    bool = false;
                } else {
                    try {
                        labWork.setTunedInWorks(Long.parseLong(sc));
                        bool = false;
                    } catch (NumberFormatException e) {
                        System.out.println("  Wrong number format");
                    }
                }
            }
            bool = true;
            while (bool) {
                System.out.print("  Average Point (long, be larger than 0, can be null): ");
                String sc = scanner.readLine().trim();
                if (sc.equals("")) {
                    bool = false;
                } else {
                    int x;
                    try {
                        x = Integer.parseInt(sc);
                        if ( x <= 0) System.out.println("  Be larger than 0!!!");
                        else { labWork.setAveragePoint(x); bool = false; }
                    } catch (NumberFormatException e) {
                        System.out.println("  Wrong number format");
                    }
                }
            }
            bool = true;
            while (bool) {
                System.out.print("  Difficulty (normal, hard, very hard, impossible, terrible, can be null): ");
                String x = scanner.readLine().trim().replace(" ", "_").toUpperCase();
                if (x.equals("")) { bool = false; }
                else {
                    try {
                        labWork.setDifficulty(Difficulty.valueOf(x));
                        bool = false;
                    } catch (IllegalArgumentException e) {
                        System.out.println("  Wrong argument");
                    }
                }
            }
            bool = true;
            while (bool){
                System.out.print("  Add Author??? 1.Yes 2.No \t");
                String sc = scanner.readLine().trim();
                String[] str = new String[4];
                if (!sc.equals("1") && !sc.equals("2")) { System.out.println("  Enter 1 or 2."); }
                else if (sc.equals("2")) { bool = false; }
                else {
                    Person author = new Person();
                    while (author.getName() == null || author.getName().equals("")) {
                        System.out.print("  Author's name (can't be empty): ");
                        try {
                            author.setName(scanner.readLine());
                        } catch (Exception ignored) {
                        }
                    }
                    while (author.getWeight() <= 0) {
                        System.out.print("  Author's weight (float, be larger than 0): ");
                        try {
                            float abc = Float.parseFloat(scanner.readLine().trim());
                            if (abc <= 0) { System.out.println("  Be larger than 0!!!"); }
                            else { author.setWeight(abc); }
                        } catch (NumberFormatException e) {
                            System.out.println("  Wrong number format"); }
                    }
                    Location location = new Location();
                    boolean localBool = true;
                    while (localBool) {
                        System.out.print("  Author Localtion (can be null, format \"x y z name\", x int, y float, z double, name (can't be longer than 236, can be null) ): ");
                        String s = scanner.readLine();
                        if (s.equals("")) { localBool = false; }
                        else {
                            int n = s.trim().split(" ").length;
                            if (n < 3) {
                                System.out.println("  Must be 3 or 4 arguments");
                            } else {
                                if (n == 3) {
                                    str[0] = s.trim().split(" ")[0];
                                    str[1] = s.trim().split(" ")[1];
                                    str[2] = s.trim().split(" ")[2];
                                } else {
                                    str = s.trim().split(" ", 4);
                                    if (str[3].length() > 236) {
                                        System.out.println("  Location name can't be longer 236 char");
                                    }
                                }
                                try {
                                    location.setX(Integer.valueOf(str[0]));
                                    location.setY(Float.parseFloat(str[1]));
                                    location.setZ(Double.valueOf(str[2]));
                                    if (str[3] != null) {
                                        location.setName(str[3]);
                                    }
                                    localBool = false;
                                } catch (NumberFormatException e) {
                                    System.out.println("  Wrong number format");
                                }
                            }
                        }
                    }
                    author.setLocation(location);
                    labWork.setAuthor(author);
                    bool = false;
                }
            }
        } catch (NoSuchElementException | IOException e) {
            System.exit(0);
        }
        return labWork;
    }
}
