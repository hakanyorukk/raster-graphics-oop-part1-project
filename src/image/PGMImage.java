package image;

/**
 * A PGM (Portable Graymap, magic number {@code P2}) image: one grey value per
 * pixel, ranging from 0 to {@code maxValue}.
 */
public class PGMImage extends SingleChannelImage {

    public PGMImage(String name, int width, int height, int maxValue, int[][] pixels) {
        super(name, width, height, maxValue, pixels);
    }

    @Override
    public String magicNumber() {
        return "P2";
    }

    @Override
    public String extension() {
        return ".pgm";
    }

    @Override
    public Image monochrome() {
        int threshold = maxValue / 2;
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                pixels[r][c] = pixels[r][c] < threshold ? 0 : maxValue;
            }
        }
        return this;
    }

    @Override
    protected SingleChannelImage makeImage(String name, int width, int height, int[][] pixels) {
        return new PGMImage(name, width, height, maxValue, pixels);
    }

    @Override
    protected void appendMaxValueLine(StringBuilder sb) {
        sb.append(maxValue).append("\n");
    }
}
