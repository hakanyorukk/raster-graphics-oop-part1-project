package command;

import java.util.HashMap;
import java.util.Map;

/** Maps command names to their {@link Command} implementations. */
public class CommandRegistry {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandRegistry() {
        commands.put("load", new LoadCommand());
        commands.put("add", new AddCommand());
        commands.put("close", new CloseCommand());
        commands.put("save", new SaveCommand());
        commands.put("saveas", new SaveAsCommand());
        commands.put("help", new HelpCommand());
        commands.put("exit", new ExitCommand());
        commands.put("grayscale", new GrayscaleCommand());
        commands.put("monochrome", new MonochromeCommand());
        commands.put("negative", new NegativeCommand());
        commands.put("rotate", new RotateCommand());
        commands.put("undo", new UndoCommand());
        commands.put("session", new SessionInfoCommand());
        commands.put("switch", new SwitchCommand());
        commands.put("collage", new CollageCommand());
    }

    /** Returns the command registered under {@code name}, or {@code null}. */
    public Command get(String name) {
        return commands.get(name);
    }
}
