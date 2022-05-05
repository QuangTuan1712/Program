package server;

import utils.NoName;
import labWork.*;
import utils.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Console implements Runnable {
    private ServerCommandReader commandReader;
    private Logger logger;
    private User user = new User("Server", "");

    public Console(ServerCommandReader commandReader, Logger logger) {
        this.commandReader = commandReader;
        this.logger = logger;
    }

    @Override
    public void run() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            try {
                String str = sc.nextLine();
                String[] cm = str.trim().split(" ", 2);
                String ans = "";
                switch (cm[0]) {
                    case "insert" :
                    case "remove_lower":
                    case "update":
                    case "replace_if_greater":
                        ans = commandReader.executeCommand(new NoName(str, createLab(), user)); break;
                    default: ans = commandReader.executeCommand(new NoName(str, user)); break;
                }
                System.out.println("  " + ans.trim() + "\n");
                logger.info("From server: " + str + "\n" + ans.trim() + ".\n");
                if (str.trim().equals("exit")) {
                    System.exit(0);
                }
            } catch (NoSuchElementException e) {
                System.exit(0);
            }
        }
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
