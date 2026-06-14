package command;

/**
 * Base class for commands that require an active session (the Template Method
 * pattern). It performs the "is a file loaded?" check in one place and only
 * then delegates to {@link #run}, so individual commands never repeat the guard.
 */
public abstract class ActiveSessionCommand implements Command {

    @Override
    public final void execute(String[] args, AppContext ctx) {
        if (ctx.sessions().getCurrentSession() == null) {
            System.out.println("No active session. Use 'load <file>' to start one.");
            return;
        }
        run(args, ctx);
    }

    /** Runs the command, guaranteed to have an active session. */
    protected abstract void run(String[] args, AppContext ctx);
}
