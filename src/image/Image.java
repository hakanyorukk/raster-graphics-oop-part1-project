package image;

/**
 * Abstract base type for every supported Netpbm image.
 * <p>
 * It holds only the data common to all formats (name and dimensions) and
 * declares the operations an image must support. Each concrete format
 * ({@link PBMImage}, {@link PGMImage}, {@link PPMImage}) provides its own
 * pixel storage and its own implementation of these operations, which removes
 * the need for {@code format.equals("P1"/"P2"/"P3")} branching elsewhere.
 */
public abstract class Image {
    protected String name;
    protected int width;
    protected int height;

    protected Image(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /** Swaps width and height (used after a 90-degree rotation). */
    protected void swapDimensions() {
        int temp = width;
        width = height;
        height = temp;
    }

    /** The Netpbm magic number of this image, e.g. {@code "P3"}. */
    public abstract String magicNumber();

    /** The canonical file extension of this image, e.g. {@code ".ppm"}. */
    public abstract String extension();

    /** Inverts the image (colour reversal) and returns the result. */
    public abstract Image negative();

    /** Rotates the image 90 degrees counter-clockwise and returns the result. */
    public abstract Image rotateLeft();

    /** Rotates the image 90 degrees clockwise and returns the result. */
    public abstract Image rotateRight();

    /** Converts the image to shades of grey (no-op for non-colour formats). */
    public abstract Image grayscale();

    /** Converts the image to black and white only and returns the result. */
    public abstract Image monochrome();

    /**
     * Builds a new image by placing {@code other} next to this one.
     *
     * @param other     the image to merge with (must be the same format and size)
     * @param direction horizontal (side by side) or vertical (stacked)
     * @param newName   the name of the resulting image
     * @return the merged image
     */
    public abstract Image collage(Image other, CollageDirection direction, String newName);

    /** Serialises this image to valid Netpbm text. */
    public abstract String toNetpbm();
}
