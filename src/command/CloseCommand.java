package command;

/** {@code close} — discards the current session. */
public class CloseCommand implements Command {

    @Override
    public void execute(String[] args, AppContext ctx) {
        if (ctx.sessions().getCurrentSession() == null) {
            System.out.println("No session is currently open.");
            return;
        }
        int closedId = ctx.sessions().getCurrentSession().getId();
        ctx.sessions().closeCurrentSession();
        System.out.println("Successfully closed session " + closedId);
    }
}
