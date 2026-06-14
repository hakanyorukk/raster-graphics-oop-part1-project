package app;

import java.util.Scanner;

/** Program entry point. */
public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Editor editor = new Editor(scanner);
        editor.start();
    }
}
