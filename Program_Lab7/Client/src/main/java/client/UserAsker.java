package client;

import utils.User;

import java.io.Console;
import java.util.Scanner;

public class UserAsker {
    Scanner sc = new Scanner(System.in);
//    Console console = System.console();
    int count = 1;

    public User askClient(String ans) {
        String login = "";
        String password = "";
        if (ans.equals("+") || ans.equals("-")) {
            login = askLogin();
            password = askPassword();
        } else if (ans.equals("*")) {
            login = "Tuan1712";
            password = "Tuan1712";
        } else {
            if (count == 5) {
                System.out.println("  Client down!!!  (╯°□°）╯︵ ┻━┻");
                System.exit(0);
            }
            System.out.print("  Enter '+' or '-'!   > ");
            count++;
        }
        return new User(login, password);
    }

    public String askLogin() {
        String login;
        while (true) {
            System.out.print("  Enter Login: ");
//            login = console.readLine();
            login = sc.nextLine();
            if (!login.trim().equals("")) {
                return login;
            }
        }
    }

    public String askPassword() {
        String password;
        while (true) {
            System.out.print("  Enter Password: ");
//            password = new String(console.readPassword());
            password = sc.nextLine();
            if (!password.trim().equals("")) {
                return password;
            }
        }
    }

}
