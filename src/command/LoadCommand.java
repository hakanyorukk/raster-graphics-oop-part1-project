package command;

import image.Image;
import session.Session;

/** {@code load <file>} — starts a new session containing the given image. */
public class LoadCommand implements Command {

    @Override
    public void execute(String[] args, AppContext ctx) {
        Args.requireLength(args, 2);
        Args.validateImageName(args[1]);
        // Load the image before creating the session, so a failed load never
        // leaves behind an empty session.
        Image image = ctx.reader().read(args[1]);
        Session session = ctx.sessions().createSession();
        session.addImage(image);
        System.out.println("Session with ID: " + session.getId() + " started");
        System.out.println("Image \"" + args[1] + "\" added");
    }
}
