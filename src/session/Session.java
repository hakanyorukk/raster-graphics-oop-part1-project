package session;

import image.Image;
import transformations.Transformation;

import java.util.ArrayList;
import java.util.List;

public class Session {
    int id;
    List<Image> images;
    List<Transformation> transformations;

    public Session(int id) {
        this.id = id;
        this.images = new ArrayList<>();
        this.transformations = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<Transformation> getTransformations() {
        return transformations;
    }

    public void addImage(Image image) {
        images.add(image);
    }
    
    public void addTransformation(Transformation transformation) {
        transformations.add(transformation);
    }
}
