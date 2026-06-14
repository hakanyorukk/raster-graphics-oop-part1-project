package command;

/** {@code session info} — prints details about the current session. */
public class SessionInfoCommand extends ActiveSessionCommand {

    @Override
    protected void run(String[] args, AppContext ctx) {
        if (args.length < 2 || !args[1].equals("info")) {
            System.out.println("Unknown session command. Did you mean 'session info'?");
            return;
        }
        System.out.println(ctx.sessions().getCurrentSession().info());
    }
}
