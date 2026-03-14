package transformations;

import image.Image;

public class Negative implements Transformation{
    @Override
    public void apply(Image image) {
        image.applyNegative();
        // invert pixels values only for pgm, ppm (max value)
    }

    @Override
    public String getName() {
        return "negative";
    }
}
