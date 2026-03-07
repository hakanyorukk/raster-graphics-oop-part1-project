package transformations;

import image.Image;

public class Monochrome implements Transformation{
    @Override
    public void apply(Image image) {
        //image.applyMonochrome();
    }

    @Override
    public String getName() {
        return "monochrome";
    }
}
