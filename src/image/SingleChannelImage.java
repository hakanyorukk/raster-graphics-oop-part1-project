package image;

/**
 * Shared base for the single-channel formats PBM ({@link PBMImage}) and
 * PGM ({@link PGMImage}), both of which store one integer per pixel in a
 * {@code int[][]} grid. The operations that are identical for the two formats
 * (negative, rotation, collage, serialisation) live here so they are written
 * only once.
 */
public abstract class SingleChannelImage extends Image {
    protected int maxValue;
    protected int[][] pixels;

    protected SingleChannelImage(String name, int width, int height, int maxValue, int[][] pixels) {
        super(name, width, height);
        this.maxValue = maxValue;
        this.pixels = pixels;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int[][] getPixels() {
        return pixels;
    }

    @Override
    public Image negative() {
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                pixels[r][c] = maxValue - pixels[r][c];
            }
        }
        return this;
    }

    @Override
    public Image rotateLeft() {
        int[][] rotated = new int[width][height];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                rotated[width - 1 - c][r] = pixels[r][c];
            }
        }
        pixels = rotated;
        swapDimensions();
        return this;
    }

    @Override
    public Image rotateRight() {
        int[][] rotated = new int[width][height];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                rotated[c][height - 1 - r] = pixels[r][c];
            }
        }
        pixels = rotated;
        swapDimensions();
        return this;
    }

    @Override
    public Image grayscale() {
        // PBM and PGM are not colour images, so grayscale leaves them unchanged.
        return this;
    }

    @Override
    public Image collage(Image other, CollageDirection direction, String newName) {
        SingleChannelImage o = (SingleChannelImage) other;
        if (direction == CollageDirection.HORIZONTAL) {
            int[][] merged = new int[height][width + o.width];
            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++) {
                    merged[r][c] = pixels[r][c];
                }
                for (int c = 0; c < o.width; c++) {
                    merged[r][width + c] = o.pixels[r][c];
                }
            }
            return makeImage(newName, width + o.width, height, merged);
        }

        int[][] merged = new int[height + o.height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                merged[r][c] = pixels[r][c];
            }
        }
        for (int r = 0; r < o.height; r++) {
            for (int c = 0; c < width; c++) {
                merged[height + r][c] = o.pixels[r][c];
            }
        }
        return makeImage(newName, width, height + o.height, merged);
    }

    @Override
    public String toNetpbm() {
        StringBuilder sb = new StringBuilder();
        sb.append(magicNumber()).append("\n");
        sb.append(width).append(" ").append(height).append("\n");
        appendMaxValueLine(sb);
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                sb.append(pixels[r][c]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /** Creates a new image of the same concrete type (used by collage). */
    protected abstract SingleChannelImage makeImage(String name, int width, int height, int[][] pixels);

    /** Appends the max-value header line if the format requires one. */
    protected abstract void appendMaxValueLine(StringBuilder sb);
}
