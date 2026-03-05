import java.util.Scanner;

public class CommandInterface {
    private Scanner scan;

    public CommandInterface(Scanner scanner) {
        this.scan = scanner;
    }

    public void start() {
        System.out.println("---------------------------------------");
        System.out.println("\t\tWelcome to the Image Reader.");
        System.out.println("---------------------------------------");
        System.out.println("Type 'help' for more information");

        while(true) {
            System.out.print("> ");
            String input = scan.nextLine();

            String[] parts = input.split(" ");
            String command = parts[0];
            if(command.equals("help")) {
                System.out.println("The following commands are supported: ");
                System.out.println("open <file> -> opens <file>");
                System.out.println("close -> closes currently opened file");
                System.out.println("save -> saves the currently open file");
                System.out.println("savesas <file> -> saves the currently open file in <file>");
                System.out.println("help -> prints this information");
                System.out.println("exit -> exists the program");
            }
            if(command.equals("exit")){
                System.out.println("Exiting program...");
                break;
            }

        }
    }
}
