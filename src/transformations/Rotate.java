package transformations;

import image.Image;

/** Rotates an image 90 degrees in a given {@link Direction}. */
public class Rotate implements Transformation {
    private final Direction direction;

    public Rotate(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Image apply(Image image) {
        return direction == Direction.LEFT ? image.rotateLeft() : image.rotateRight();
    }

    @Override
    public String getName() {
        return "rotate " + direction.label();
    }
}
