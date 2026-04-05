package session;

import image.Image;
import transformations.Transformation;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private int id;
    private List<Image> images;
    private List<Transformation> transformations;

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

    public void applyTransformations() {
        for(Transformation transformation : transformations) {
            for(Image image : images) {
                transformation.apply(image);
            }
        }
        // save each image after all transformations applied
        for(Image image: images) {
            image.saveImage();
        }
        // clear transformations from the memory
        transformations.clear();
    }

    public void saveAs(String newName) {
        Image first = images.get(0);
        for(Transformation transformation : transformations) {
            transformation.apply(first);
        }
        first.saveImageAs(newName);
        transformations.clear();
    }

    public void undo(){
        transformations.removeLast();
    }

    public Image findImage(String name) {
        for(Image img : images) {
            if(img.getName().equals(name)) {
                return img;
            }
        }
        return null;
    }
}
