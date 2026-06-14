package command;

/**
 * A single console command (the Command design pattern). Each supported command
 * is its own class, so adding a new command means adding one class and
 * registering it in {@link CommandRegistry} — no existing code needs to change.
 */
public interface Command {

    /**
     * Executes the command.
     *
     * @param args the whole input split into tokens; {@code args[0]} is the
     *             command name itself
     * @param ctx  access to the application's services
     */
    void execute(String[] args, AppContext ctx);
}
