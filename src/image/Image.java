package image;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Image {
    private String imageName;
    private String format; // -> p1, p2, p3
    private int width;
    private int height;
    private int maxValue;

    private int [][] pixels; // -> pbm, pgm
    private int[][][] colorPixels; // -> ppm

    public Image(String imageName) {
        this.imageName = imageName;
        loadImage(imageName);
    }

    public String getName() {
        return imageName;
    }

    public void loadImage(String imageName) {
        ArrayList<String> lines = new ArrayList<>();

        try (Scanner scanner = new Scanner(Paths.get(imageName))) {
            this.format = scanner.next();
            this.width = scanner.nextInt();
            this.height = scanner.nextInt();

            if(format.equals("P1")) {
                loadPBM(scanner);
            }

            if(format.equals("P2")) {
                this.maxValue = scanner.nextInt();
                loadPGM(scanner);
            }

            if(format.equals("P3")) {
                this.maxValue = scanner.nextInt();
                loadPPM(scanner);
            }

            // if values e.g. format.equlad("P2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(lines);
        for(String line: lines) {
            System.out.println(line);
        }
    }

    public void loadPBM(Scanner scanner) {
        pixels = new int[height][width];
        for(int row = 0; row< height; row++){
            for(int column = 0; column<width; column++) {
                pixels[row][column] = scanner.nextInt();
            }
        }
    }

    public void loadPGM(Scanner scanner) {
        pixels = new int[height][width];
        for(int row = 0; row< height; row++){
            for(int column = 0; column<width; column++) {
                pixels[row][column] = scanner.nextInt();
            }
        }
    }

    public void loadPPM(Scanner scanner) {
        colorPixels = new int[height][width][3];
        for(int row = 0; row< height; row++){
            for(int column = 0; column<width; column++) {
                colorPixels[row][column][0] = scanner.nextInt();
                colorPixels[row][column][1] = scanner.nextInt();
                colorPixels[row][column][2] = scanner.nextInt();
            }
        }
    }


    // load pbm P1 -> pixel bitmap black, white  0-1
    // load pgm P2 -> pixel graymap             0-255
    // load ppm P3 -> pixels in the 2d array -> pixels came form width height // pixel pixmap  rgb


    // printing images // it should return void or string?
    public void printImage() {
        // printing only the pixels p1 and p2 same pbm, pgm
        // pbm || pgm
        if(format.equals("P1") || format.equals("P2")) {
            System.out.println(format);
            System.out.println(width);
            System.out.println(height);
            for(int row = 0; row< height; row++) {
                for(int column = 0; column < width; column++) {
                    System.out.print(pixels[row][column] + " ");
                }
            System.out.println();
            }
        }
        // ppm
        if(format.equals("P3")) {
            System.out.println(format);
            System.out.println(width);
            System.out.println(height);
            // with colors
            for(int row=0; row< height; row++) {
                for(int column =0; column< width; column++) {
                    System.out.print(
                            " ( " +
                                    colorPixels[row][column][0] +
                                    colorPixels[row][column][1] +
                                    colorPixels[row][column][2] + ")"
                    );
                }
                System.out.println();
            }
        }

    }
}
