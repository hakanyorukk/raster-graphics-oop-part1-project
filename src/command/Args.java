package command;

import exceptions.FalseUsage;
import exceptions.InvalidImageName;

import java.util.Set;

/** Small validation helpers shared by the command classes. */
public final class Args {
    private static final Set<String> SUPPORTED_EXTENSIONS = Set.of("ppm", "pgm", "pbm");

    private Args() {
    }

    /**
     * Ensures the input has at least {@code expected} tokens.
     *
     * @throws FalseUsage if too few tokens were supplied
     */
    public static void requireLength(String[] args, int expected) {
        if (args.length < expected) {
            throw new FalseUsage("Invalid usage: '" + args[0] + "' expects " + (expected - 1) + " argument(s).");
        }
    }

    /**
     * Ensures a file name has a supported Netpbm extension.
     *
     * @throws InvalidImageName if the name has no extension or an unsupported one
     */
    public static void validateImageName(String name) {
        if (name == null || !name.contains(".")) {
            throw new InvalidImageName("Invalid image name: " + name);
        }
        String extension = name.substring(name.lastIndexOf('.') + 1);
        if (!SUPPORTED_EXTENSIONS.contains(extension)) {
            throw new InvalidImageName("Unsupported extension: ." + extension + " (use .ppm, .pgm or .pbm)");
        }
    }
}
