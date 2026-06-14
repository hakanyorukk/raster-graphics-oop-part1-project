package command;

import exceptions.InvalidImageName;
import image.Image;
import session.Session;

/** {@code add <file>} — adds another image to the current session. */
public class AddCommand extends ActiveSessionCommand {

    @Override
    protected void run(String[] args, AppContext ctx) {
        Args.requireLength(args, 2);
        Args.validateImageName(args[1]);
        Session session = ctx.sessions().getCurrentSession();
        if (session.findImage(args[1]) != null) {
            throw new InvalidImageName("Image already added!");
        }
        Image image = ctx.reader().read(args[1]);
        session.addImage(image);
        System.out.println("Image \"" + args[1] + "\" added");
    }
}
