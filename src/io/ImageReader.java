package io;

import exceptions.InvalidImageName;
import image.Image;
import image.PBMImage;
import image.PGMImage;
import image.PPMImage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Reads a Netpbm file from disk and builds the matching {@link Image} subtype.
 * This is the single place in the program that decides which concrete image
 * class to create, based on the file's magic number.
 */
public class ImageReader {

    /**
     * Reads the image stored at {@code path}.
     *
     * @throws InvalidImageName if the file cannot be opened, has an unknown
     *                          magic number, or contains incomplete data
     */
    public Image read(String path) {
        try (Scanner scanner = new Scanner(Paths.get(path))) {
            String magic = scanner.next();
            return switch (magic) {
                case "P1" -> readPbm(path, scanner);
                case "P2" -> readPgm(path, scanner);
                case "P3" -> readPpm(path, scanner);
                default -> throw new InvalidImageName("Unsupported image format \"" + magic + "\" in " + path);
            };
        } catch (IOException e) {
            throw new InvalidImageName("Could not open file \"" + path + "\"");
        } catch (NoSuchElementException e) {
            throw new InvalidImageName("Corrupted or incomplete image data in \"" + path + "\"");
        }
    }

    private Image readPbm(String name, Scanner scanner) {
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        int[][] pixels = new int[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                pixels[r][c] = scanner.nextInt();
            }
        }
        return new PBMImage(name, width, height, pixels);
    }

    private Image readPgm(String name, Scanner scanner) {
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        int maxValue = scanner.nextInt();
        int[][] pixels = new int[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                pixels[r][c] = scanner.nextInt();
            }
        }
        return new PGMImage(name, width, height, maxValue, pixels);
    }

    private Image readPpm(String name, Scanner scanner) {
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        int maxValue = scanner.nextInt();
        int[][][] pixels = new int[height][width][3];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                pixels[r][c][0] = scanner.nextInt();
                pixels[r][c][1] = scanner.nextInt();
                pixels[r][c][2] = scanner.nextInt();
            }
        }
        return new PPMImage(name, width, height, maxValue, pixels);
    }
}
