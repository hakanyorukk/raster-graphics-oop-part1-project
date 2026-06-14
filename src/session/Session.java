package session;

import image.Image;
import transformations.Transformation;

import java.util.ArrayList;
import java.util.List;

/**
 * A user session: a set of loaded images plus a queue of transformations that
 * are pending until the user saves. Each session has a unique id.
 */
public class Session {
    private final int id;
    private final List<Image> images = new ArrayList<>();
    private final List<Transformation> transformations = new ArrayList<>();

    public Session(int id) {
        this.id = id;
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

    /** The image that was loaded first (the one {@code saveas} writes). */
    public Image firstImage() {
        return images.get(0);
    }

    /** Finds a loaded image by name, or returns {@code null} if absent. */
    public Image findImage(String name) {
        for (Image image : images) {
            if (image.getName().equals(name)) {
                return image;
            }
        }
        return null;
    }

    /**
     * Applies every pending transformation to every image, replacing each image
     * with the transformed result, then clears the queue.
     */
    public void applyPending() {
        for (Transformation transformation : transformations) {
            for (int i = 0; i < images.size(); i++) {
                images.set(i, transformation.apply(images.get(i)));
            }
        }
        transformations.clear();
    }

    /**
     * Removes the most recently queued transformation.
     *
     * @return the removed transformation, or {@code null} if the queue was empty
     */
    public Transformation undo() {
        if (transformations.isEmpty()) {
            return null;
        }
        return transformations.removeLast();
    }

    /** Builds the multi-line description shown by {@code session info}. */
    public String info() {
        StringBuilder sb = new StringBuilder("Name of images in the session: ");
        for (Image image : images) {
            sb.append(image.getName()).append(" ");
        }
        sb.append("\nPending transformations: ");
        if (transformations.isEmpty()) {
            sb.append("No transformation added yet!");
        } else {
            for (int i = 0; i < transformations.size(); i++) {
                sb.append(transformations.get(i).getName());
                if (i < transformations.size() - 1) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }
}
