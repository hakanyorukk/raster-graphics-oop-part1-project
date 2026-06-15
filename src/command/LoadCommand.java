package command;

import image.Image;
import session.Session;

import java.util.ArrayList;
import java.util.List;

/** {@code load <file> [<file> ...]} — starts a new session containing the given image(s). */
public class LoadCommand implements Command {

    @Override
    public void execute(String[] args, AppContext ctx) {
        Args.requireLength(args, 2);
        List<Image> images = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            Args.validateImageName(args[i]);
            images.add(ctx.reader().read(args[i]));
        }
        Session session = ctx.sessions().createSession();
        System.out.println("Session with ID: " + session.getId() + " started");
        for (Image image : images) {
            session.addImage(image);
            System.out.println("Image \"" + image.getName() + "\" added");
        }
    }
}
