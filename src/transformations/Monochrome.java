package transformations;

import image.Image;

public class Monochrome implements Transformation{
    @Override
    public void apply(Image image) {
        image.applyMonochrome();
        // only black or white pixels
        // threshold = maxValue / 2 => 128 50% density
        // pixel > threshold => white(255)
        // pixel < threshold => black (0)
    }

    @Override
    public String getName() {
        return "monochrome";
    }

    @Override
    public String toString() {
        return "Monochrome";
    }
}
