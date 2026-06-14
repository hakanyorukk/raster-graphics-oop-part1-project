package command;

/** {@code help} — lists every supported command. */
public class HelpCommand implements Command {

    @Override
    public void execute(String[] args, AppContext ctx) {
        System.out.println("The following commands are supported:");
        System.out.println("load <file>            loads <file> and starts a new session");
        System.out.println("add <file>             adds an image to the current session");
        System.out.println("close                  closes the current session");
        System.out.println("save                   applies transformations and saves all images");
        System.out.println("saveas <file>          saves the first image of the session to <file>");
        System.out.println("grayscale              queues a grayscale transformation");
        System.out.println("monochrome             queues a monochrome transformation");
        System.out.println("negative               queues a negative transformation");
        System.out.println("rotate <left|right>    queues a 90-degree rotation");
        System.out.println("undo                   removes the last queued transformation");
        System.out.println("session info           shows details of the current session");
        System.out.println("switch <id>            switches to the session with the given id");
        System.out.println("collage <direction> <image1> <image2> <outimage>  creates a collage");
        System.out.println("help                   prints this information");
        System.out.println("exit                   exits the program");
    }
}
