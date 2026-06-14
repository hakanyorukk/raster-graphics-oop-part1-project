package command;

/** {@code exit} — stops the program. */
public class ExitCommand implements Command {

    @Override
    public void execute(String[] args, AppContext ctx) {
        System.out.println("Exiting the program...");
        ctx.stop();
    }
}
