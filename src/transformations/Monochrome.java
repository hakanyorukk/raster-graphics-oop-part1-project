package transformations;

import image.Image;

/** Converts images to black and white only (no shades of grey). */
public class Monochrome implements Transformation {

    @Override
    public Image apply(Image image) {
        return image.monochrome();
    }

    @Override
    public String getName() {
        return "monochrome";
    }
}
