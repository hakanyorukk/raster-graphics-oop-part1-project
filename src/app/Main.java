package app;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        CommandInterface ui = new CommandInterface(scan);
        ui.start();

        // 1. undo transaction
        // 2. refactor commandInterface - use switch
        // 3. complete collage command
    }
}
