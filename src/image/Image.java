package image;

import java.io.PrintWriter;
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

    public int[][] getPixels() {
        return pixels;
    }

    public String getName() {
        return imageName;
    }

    public void loadImage(String imageName) {

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

    }
    //pixels in the 2d array -> pixels came form width height //
    // load pbm P1 -> pixel bitmap black, white  0-1
    // load pgm P2 -> pixel graymap              0-255
    // load ppm P3 -> pixel pixmap               rgb

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

    public void rotate(String direction) {
        if(direction.equals("left")) {
            rotateLeft();
        } else if (direction.equals("right")) {
            rotateRight();
        }
    }

    public void rotateLeft() {
        // -90 transpose() -> reverseCols

        if(format.equals("P3")) {
            transposeColor(colorPixels);
            reverseColsColor(colorPixels);
        } else {
            transpose(pixels);
            reverseCols(pixels);
        }
        int temp = width;
        width = height;
        height = temp;
    }

    public void rotateRight() {
        // +90 transpose()  -> reverseRows
        if(format.equals("P3")) {
            transposeColor(colorPixels);
            reverseRowsColor(colorPixels);
        } else {
            transpose(pixels);
            reverseRows(pixels);
        }
        int temp = height;
        height = width;
        width = temp;
    }

    public void transpose(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = row + 1; col < matrix.length; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = temp;
            }
        }
    }

    public void transposeColor(int [][][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = row + 1; col < matrix.length; col++) {
                for(int i =0; i < 3; i++) {
                    int temp = matrix[row][col][i];
                    matrix[row][col][i] = matrix[col][row][i];
                    matrix[col][row][i] = temp;
                }
            }
        }
    }

    public void reverseRows(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length / 2; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[row][matrix[row].length - col - 1];
                matrix[row][matrix[row].length - col - 1] = temp;
            }
        }
    }

    public void reverseRowsColor(int[][][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length / 2; col++) {
                for(int i = 0; i< 3; i++) {
                    int temp = matrix[row][col][i];
                    matrix[row][col][i] = matrix[row][matrix[row].length - col - 1][i];
                    matrix[row][matrix[row].length - col - 1][i] = temp;
                }

            }
        }
    }

    public void reverseCols(int[][] matrix) {
        for(int col = 0; col < matrix[0].length; col++) {
            for(int row = 0; row < matrix.length / 2; row++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[matrix.length - row - 1][col];
                matrix[matrix.length - row - 1][col] = temp;
            }
        }
    }

    public void reverseColsColor(int[][][] matrix) {
        for(int col = 0; col < matrix[0].length; col++) {
            for(int row = 0; row < matrix.length / 2; row++) {
                for(int i = 0; i< 3; i++) {
                    int temp = matrix[row][col][i];
                    matrix[row][col][i] = matrix[matrix.length - row - 1][col][i];
                    matrix[matrix.length - row - 1][col][i] = temp;
                }

            }
        }
    }


    public void applyNegative() {
        // newVal = maxVal - oldValue(pixel)
        switch (format) {
            case "P1" -> {
                for (int r = 0; r < height; r++) {
                    for (int c = 0; c < width; c++) {
                        pixels[r][c] = 1 - pixels[r][c];
                    }
                }
            }
            case "P2" -> {
                for (int r = 0; r < height; r++) {
                    for (int c = 0; c < width; c++) {
                        pixels[r][c] = maxValue - pixels[r][c];
                    }
                }
            }
            case "P3" -> {
                for (int r = 0; r < height; r++) {
                    for (int c = 0; c < width; c++) {
                        for (int i = 0; i < 3; i++) {
                            colorPixels[r][c][i] = maxValue - colorPixels[r][c][i];
                        }
                    }
                }
            }
        }
    }

    public void applyGrayScale () {
        if (!format.equals("P3")) return;

        pixels = new int[height][width];
        for(int r = 0; r < height; r++) {
            for(int c = 0; c< width; c++) {
                int rVal = colorPixels[r][c][0];
                int gVal = colorPixels[r][c][1];
                int bVal = colorPixels[r][c][2];

                int gray = (rVal + gVal + bVal) / 3;
                pixels[r][c] = gray;
            }
        }
        colorPixels = null;
        format = "P2";
    }

    public void applyMonochrome() {
        // pgm, ppm

        // convert to p2 grayscale
        if(format.equals("P3")) {
            applyGrayScale();
        }
        int threshold = maxValue / 2; // 50% density

        if(format.equals("P2")) {
            for(int row = 0; row < height; row++) {
                for (int column = 0; column < width; column++) {
                    if( pixels[row][column] < threshold) {
                        pixels[row][column] = 0;
                    } else {
                        pixels[row][column] = 255;
                    }
                }
            }
        }

    }

    public void saveImage() {
        try (PrintWriter writer = new PrintWriter(imageName)) {
            writer.println(format);
            writer.println(width + " " + height);

            if(!format.equals("P1")) {
                writer.println(maxValue);
            }

            if (format.equals("P3")) {
                for (int row = 0; row < height; row++) {
                    for (int col = 0; col < width; col++) {
                        writer.print(colorPixels[row][col][0] + " ");
                        writer.print(colorPixels[row][col][1] + " ");
                        writer.print(colorPixels[row][col][2] + " ");
                    }
                    writer.println();
                }
            } else {
                for (int row = 0; row < height; row++) {
                    for (int col = 0; col < width; col++) {
                        writer.print(pixels[row][col] + " ");
                    }
                    writer.println();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
