package command;

import image.Image;
import session.Session;

/** {@code save} — applies pending transformations and saves every image. */
public class SaveCommand extends ActiveSessionCommand {

    @Override
    protected void run(String[] args, AppContext ctx) {
        Session session = ctx.sessions().getCurrentSession();
        session.applyPending();
        for (Image image : session.getImages()) {
            ctx.writer().write(image, image.getName());
            System.out.println("Successfully saved \"" + image.getName() + "\"");
        }
    }
}
