package io;

import exceptions.InvalidImageName;
import image.Image;

import java.io.PrintWriter;

/**
 * Writes an {@link Image} to disk as a valid Netpbm file. The textual shape of
 * each format is produced by the image itself ({@link Image#toNetpbm()}); this
 * class only deals with the filesystem.
 */
public class ImageWriter {

    /**
     * Writes {@code image} to {@code path}.
     *
     * @throws InvalidImageName if the file cannot be written
     */
    public void write(Image image, String path) {
        try (PrintWriter writer = new PrintWriter(path)) {
            writer.print(image.toNetpbm());
        } catch (Exception e) {
            throw new InvalidImageName("Could not write file \"" + path + "\"");
        }
    }
}
