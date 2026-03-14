package transformations;

import image.Image;

public class Grayscale implements Transformation{
    @Override
    public void apply(Image image) {
        image.applyGrayScale();
        // only for ppm format (color images)
        // gray = (R + G + B) / 3
    }

    @Override
    public String getName() {
        return "grayscale";
    }

}
