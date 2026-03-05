import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        CommandInterface ui = new CommandInterface(scan);
        ui.start();
    }
}
