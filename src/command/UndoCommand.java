package command;

import transformations.Transformation;

/** {@code undo} — removes the last queued transformation (no-op if none). */
public class UndoCommand extends ActiveSessionCommand {

    @Override
    protected void run(String[] args, AppContext ctx) {
        Transformation removed = ctx.sessions().getCurrentSession().undo();
        if (removed == null) {
            System.out.println("Nothing to undo.");
        } else {
            System.out.println("Undid: " + removed.getName());
        }
    }
}
