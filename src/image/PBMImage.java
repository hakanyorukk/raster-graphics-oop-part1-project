package image;

/**
 * A PBM (Portable Bitmap, magic number {@code P1}) image: one bit per pixel,
 * where every value is either 0 or 1. Its maximum value is therefore fixed at 1.
 */
public class PBMImage extends SingleChannelImage {

    public PBMImage(String name, int width, int height, int[][] pixels) {
        super(name, width, height, 1, pixels);
    }

    @Override
    public String magicNumber() {
        return "P1";
    }

    @Override
    public String extension() {
        return ".pbm";
    }

    @Override
    public Image monochrome() {
        // A PBM already contains only black and white pixels.
        return this;
    }

    @Override
    protected SingleChannelImage makeImage(String name, int width, int height, int[][] pixels) {
        return new PBMImage(name, width, height, pixels);
    }

    @Override
    protected void appendMaxValueLine(StringBuilder sb) {
        // PBM files have no max-value line.
    }
}
