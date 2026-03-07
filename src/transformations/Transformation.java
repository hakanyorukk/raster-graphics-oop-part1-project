package transformations;

import image.Image;

public interface Transformation {
    void apply(Image image);
    String getName();
}
