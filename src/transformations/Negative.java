package transformations;

import image.Image;

/** Produces the negative (colour reversal) of an image. */
public class Negative implements Transformation {

    @Override
    public Image apply(Image image) {
        return image.negative();
    }

    @Override
    public String getName() {
        return "negative";
    }
}
