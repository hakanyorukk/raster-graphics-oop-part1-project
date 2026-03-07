package transformations;

import image.Image;

public class Negative implements Transformation{
    @Override
    public void apply(Image image) {
        //image.applyNegative();
    }

    @Override
    public String getName() {
        return "negative";
    }
}
