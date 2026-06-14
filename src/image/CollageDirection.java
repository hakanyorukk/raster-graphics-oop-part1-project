package image;

import exceptions.InvalidDirection;

/** The two ways two images can be joined into a collage. */
public enum CollageDirection {
    HORIZONTAL,
    VERTICAL;

    /**
     * Parses a user-supplied direction.
     *
     * @throws InvalidDirection if the text is neither "horizontal" nor "vertical"
     */
    public static CollageDirection parse(String text) {
        return switch (text.toLowerCase()) {
            case "horizontal" -> HORIZONTAL;
            case "vertical" -> VERTICAL;
            default -> throw new InvalidDirection("Invalid direction: " + text + ". Use horizontal or vertical.");
        };
    }
}
