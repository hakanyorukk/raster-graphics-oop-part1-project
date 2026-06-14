package command;

import session.SessionManager;

/** {@code switch <id>} — makes another session the current one. */
public class SwitchCommand implements Command {

    @Override
    public void execute(String[] args, AppContext ctx) {
        Args.requireLength(args, 2);
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Session ID must be a number.");
            return;
        }
        SessionManager sessions = ctx.sessions();
        if (!sessions.hasSession(id)) {
            System.out.println("Session with ID: " + id + " does not exist!");
            return;
        }
        sessions.switchSession(id);
        System.out.println("You switched to session with ID: " + id + "!");
        System.out.println(sessions.getCurrentSession().info());
    }
}
