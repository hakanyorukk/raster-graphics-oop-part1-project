package transformations;

import image.Image;

public class Grayscale implements Transformation{
    @Override
    public void apply(Image image) {
        //image.applyGrayScale();
    }

    @Override
    public String getName() {
        return "grayscale";
    }
}
