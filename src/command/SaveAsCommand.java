package command;

import image.Image;
import session.Session;

/** {@code saveas <file>} — saves only the first image under a new path. */
public class SaveAsCommand extends ActiveSessionCommand {

    @Override
    protected void run(String[] args, AppContext ctx) {
        Args.requireLength(args, 2);
        Session session = ctx.sessions().getCurrentSession();
        session.applyPending();
        Image first = session.firstImage();
        String path = args[1];
        ctx.writer().write(first, path);
        System.out.println("Successfully saved \"" + path + "\"");
    }
}
