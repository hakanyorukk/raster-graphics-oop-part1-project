package image;

/**
 * A PPM (Portable Pixmap, magic number {@code P3}) image: three colour
 * channels (red, green, blue) per pixel, each ranging from 0 to {@code maxValue}.
 */
public class  PPMImage extends Image {
    private int maxValue;
    private int[][][] pixels;

    public PPMImage(String name, int width, int height, int maxValue, int[][][] pixels) {
        super(name, width, height);
        this.maxValue = maxValue;
        this.pixels = pixels;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int[][][] getPixels() {
        return pixels;
    }

    @Override
    public String magicNumber() {
        return "P3";
    }

    @Override
    public String extension() {
        return ".ppm";
    }

    @Override
    public Image negative() {
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                for (int i = 0; i < 3; i++) {
                    pixels[r][c][i] = maxValue - pixels[r][c][i];
                }
            }
        }
        return this;
    }

    @Override
    public Image rotateLeft() {
        int[][][] rotated = new int[width][height][3];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                for (int i = 0; i < 3; i++) {
                    rotated[width - 1 - c][r][i] = pixels[r][c][i];
                }
            }
        }
        pixels = rotated;
        swapDimensions();
        return this;
    }

    @Override
    public Image rotateRight() {
        int[][][] rotated = new int[width][height][3];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                for (int i = 0; i < 3; i++) {
                    rotated[c][height - 1 - r][i] = pixels[r][c][i];
                }
            }
        }
        pixels = rotated;
        swapDimensions();
        return this;
    }

    @Override
    public Image grayscale() {
        // A PPM stays a PPM; every pixel becomes a shade of grey (R = G = B).
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                int gray = (pixels[r][c][0] + pixels[r][c][1] + pixels[r][c][2]) / 3;
                pixels[r][c][0] = gray;
                pixels[r][c][1] = gray;
                pixels[r][c][2] = gray;
            }
        }
        return this;
    }

    @Override
    public Image monochrome() {
        int threshold = maxValue / 2;
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                int gray = (pixels[r][c][0] + pixels[r][c][1] + pixels[r][c][2]) / 3;
                int value = gray < threshold ? 0 : maxValue;
                pixels[r][c][0] = value;
                pixels[r][c][1] = value;
                pixels[r][c][2] = value;
            }
        }
        return this;
    }

    @Override
    public Image collage(Image other, CollageDirection direction, String newName) {
        PPMImage o = (PPMImage) other;
        if (direction == CollageDirection.HORIZONTAL) {
            int[][][] merged = new int[height][width + o.width][3];
            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++) {
                    for (int i = 0; i < 3; i++) {
                        merged[r][c][i] = pixels[r][c][i];
                    }
                }
                for (int c = 0; c < o.width; c++) {
                    for (int i = 0; i < 3; i++) {
                        merged[r][width + c][i] = o.pixels[r][c][i];
                    }
                }
            }
            return new PPMImage(newName, width + o.width, height, maxValue, merged);
        }

        int[][][] merged = new int[height + o.height][width][3];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                for (int i = 0; i < 3; i++) {
                    merged[r][c][i] = pixels[r][c][i];
                }
            }
        }
        for (int r = 0; r < o.height; r++) {
            for (int c = 0; c < width; c++) {
                for (int i = 0; i < 3; i++) {
                    merged[height + r][c][i] = o.pixels[r][c][i];
                }
            }
        }
        return new PPMImage(newName, width, height + o.height, maxValue, merged);
    }

    @Override
    public String toNetpbm() {
        StringBuilder sb = new StringBuilder();
        sb.append("P3\n").append(width).append(" ").append(height).append("\n").append(maxValue).append("\n");
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                sb.append(pixels[r][c][0]).append(" ")
                  .append(pixels[r][c][1]).append(" ")
                  .append(pixels[r][c][2]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
