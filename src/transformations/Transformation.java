package transformations;

import image.Image;

/**
 * A single image operation that can be queued in a session and applied later.
 * Applying a transformation returns the resulting image, which may be a new
 * instance, so the caller should replace the original with the returned value.
 */
public interface Transformation {

    /** Applies this transformation and returns the resulting image. */
    Image apply(Image image);

    /** Human-readable name shown by {@code session info}, e.g. "rotate left". */
    String getName();
}
