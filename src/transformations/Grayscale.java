package transformations;

import image.Image;

/** Converts colour images to shades of grey. */
public class Grayscale implements Transformation {

    @Override
    public Image apply(Image image) {
        return image.grayscale();
    }

    @Override
    public String getName() {
        return "grayscale";
    }
}
