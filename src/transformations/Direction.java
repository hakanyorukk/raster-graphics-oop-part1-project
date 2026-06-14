package transformations;

import exceptions.InvalidDirection;

/** The direction of a 90-degree rotation. */
public enum Direction {
    LEFT,
    RIGHT;

    /**
     * Parses a user-supplied direction.
     *
     * @throws InvalidDirection if the text is neither "left" nor "right"
     */
    public static Direction parse(String text) {
        return switch (text.toLowerCase()) {
            case "left" -> LEFT;
            case "right" -> RIGHT;
            default -> throw new InvalidDirection("Invalid direction: " + text + ". Use left or right.");
        };
    }

    /** The lower-case label of this direction, e.g. "left". */
    public String label() {
        return name().toLowerCase();
    }
}
