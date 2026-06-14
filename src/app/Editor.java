package app;

import command.AppContext;
import command.Command;
import command.CommandRegistry;
import exceptions.FalseUsage;
import exceptions.InvalidDirection;
import exceptions.InvalidImageName;
import io.ImageReader;
import io.ImageWriter;
import session.SessionManager;

import java.util.Scanner;

/**
 * The console application: a read-eval-print loop that reads a line, looks up
 * the matching {@link Command}, and runs it. All command-specific logic lives in
 * the {@code command} package, so this class stays small. It also implements
 * {@link AppContext} to give commands access to the shared services.
 */

public class Editor implements AppContext {
    private final Scanner scanner;
    private final SessionManager sessions = new SessionManager();
    private final ImageReader reader = new ImageReader();
    private final ImageWriter writer = new ImageWriter();
    private final CommandRegistry registry = new CommandRegistry();
    private boolean running = true;

    public Editor(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public SessionManager sessions() {
        return sessions;
    }

    @Override
    public ImageReader reader() {
        return reader;
    }

    @Override
    public ImageWriter writer() {
        return writer;
    }

    @Override
    public void stop() {
        running = false;
    }

    /** Runs the command loop until {@code exit} or end of input. */
    public void start() {
        System.out.println("\t\tWelcome to the Image Reader.");
        System.out.println("Type 'help' for more information");

        while (running) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                System.out.println();
                break;
            }

            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] tokens = line.split("\\s+");
            Command command = registry.get(tokens[0]);
            if (command == null) {
                System.out.println("Unknown command: " + tokens[0] + ". Type 'help' for available commands.");
                continue;
            }

            try {
                command.execute(tokens, this);
            } catch (FalseUsage | InvalidImageName | InvalidDirection e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
