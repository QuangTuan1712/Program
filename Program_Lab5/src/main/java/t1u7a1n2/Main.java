package t1u7a1n2;

public class Main {
    public static void main(String args[]) {
        try {
            Reader reader = new Reader(args[0].trim());
//            Reader reader = new Reader("D:/Prog/Lab5/test.xml");
            System.out.println("-------------------------------------------------------");
            reader.run();
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("No such file exist");
        }
    }
}