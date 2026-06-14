package command;

import exceptions.InvalidImageName;
import image.CollageDirection;
import image.Image;
import session.Session;

/**
 * {@code collage <direction> <image1> <image2> <outimage>} — joins two images
 * of the same format and size into a new image added to the current session.
 */
public class CollageCommand extends ActiveSessionCommand {

    @Override
    protected void run(String[] args, AppContext ctx) {
        Args.requireLength(args, 5);
        CollageDirection direction = CollageDirection.parse(args[1]);
        String firstName = args[2];
        String secondName = args[3];
        String outName = args[4];

        Session session = ctx.sessions().getCurrentSession();
        Image first = session.findImage(firstName);
        Image second = session.findImage(secondName);

        if (first == null || second == null) {
            throw new InvalidImageName("One or both images are not in the current session.");
        }
        if (!first.magicNumber().equals(second.magicNumber())) {
            throw new InvalidImageName("Cannot make a collage from different types! ("
                    + first.extension() + " and " + second.extension() + ")");
        }
        if (first.getWidth() != second.getWidth() || first.getHeight() != second.getHeight()) {
            throw new InvalidImageName("Cannot make a collage from images of different sizes!");
        }

        Image result = first.collage(second, direction, outName);
        session.addImage(result);
        System.out.println("New collage \"" + outName + "\" created");
    }
}
