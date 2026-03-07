package transformations;

import image.Image;

public class Rotate implements Transformation{
    private String direction;

    public Rotate(String direction) {
        this.direction = direction;
    }

    @Override
    public void apply(Image image) {
       // image.rotate(direction);
    }

    @Override
    public String getName() {
        return "rotate " + direction;
    }
}
