package image;

import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
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

    // p1 pbm
    public Image(String imageName, String format, int width, int height, int[][] pixels) {
        this.imageName = imageName;
        this.format = format;
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    // p2 pgm
    public Image(String imageName, String format, int width, int height, int maxValue, int[][] pixels) {
        this.imageName = imageName;
        this.format = format;
        this.width = width;
        this.maxValue = maxValue;
        this.height = height;
        this.pixels = pixels;
    }

    // p3 ppm
    public Image(String imageName, String format, int width, int height, int maxValue, int[][][] colorPixels) {
        this.imageName = imageName;
        this.format = format;
        this.width = width;
        this.height = height;
        this.maxValue = maxValue;
        this.colorPixels = colorPixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int[][][] getColorPixels() {
        return colorPixels;
    }

    public int[][] getPixels() {
        return pixels;
    }

    public String getName() {
        return imageName;
    }

    public String getFormat() {
        return format;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return width == image.width && height == image.height && maxValue == image.maxValue && Objects.equals(imageName, image.imageName) && Objects.equals(format, image.format) && Objects.deepEquals(pixels, image.pixels) && Objects.deepEquals(colorPixels, image.colorPixels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageName, format, width, height, maxValue, Arrays.deepHashCode(pixels), Arrays.deepHashCode(colorPixels));
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //pixels in the 2d array -> pixels came form width height //
    // load pbm P1 -> pixel bitmap black, white  0-1
    // load pgm P2 -> pixel graymap              0-255
    // load ppm P3 -> pixel Pixmap               rgb

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

    public String printImage() {
        // printing only the pixels p1 and p2 same pbm, pgm
        // pbm || pgm
        StringBuilder result = new StringBuilder();
        if(format.equals("P1") || format.equals("P2")) {
            result.append(format).append("\n").append(width).append("\n").append(height).append("\n");
            for(int row = 0; row < height; row++) {
                for(int column = 0; column < width; column++) {
                    result.append(pixels[row][column]).append(" ");
                }
                result.append("\n");
            }
        }
        // ppm
        if(format.equals("P3")) {
            result.append(format).append("\n").append(width).append("\n").append(height).append("\n");
            // with colors
            for(int row=0; row< height; row++) {
                for(int column =0; column< width; column++) {
                    result.append(" ( ")
                            .append(colorPixels[row][column][0])
                            .append(colorPixels[row][column][1])
                            .append(colorPixels[row][column][2])
                                    .append(")");
                }
                result.append("\n");
            }
        }
        return result.toString();
    }

    public void rotate(String direction) {
        if(direction.equals("left")) {
            rotateLeft();
        } else if (direction.equals("right")) {
            rotateRight();
        }
    }

    public void rotateLeft() {
        if(format.equals("P3")) {
            colorPixels = transposeColor(colorPixels); // ← assign back!
            reverseColsColor(colorPixels);
        } else {
            pixels = transpose(pixels); //  assign back!
            reverseCols(pixels);
        }
        int temp = width;
        width = height;
        height = temp;
    }

    public void rotateRight() {
        if(format.equals("P3")) {
            colorPixels = transposeColor(colorPixels); //  assign back!
            reverseRowsColor(colorPixels);
        } else {
            pixels = transpose(pixels); //  assign back!
            reverseRows(pixels);
        }
        int temp = width;
        width = height;
        height = temp;
    }

    public int[][] transpose(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] result = new int[cols][rows]; // swapped dimensions!
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                result[col][row] = matrix[row][col];
            }
        }
        return result;
    }

    public int[][][] transposeColor(int[][][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][][] result = new int[cols][rows][3];
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                result[col][row][0] = matrix[row][col][0];
                result[col][row][1] = matrix[row][col][1];
                result[col][row][2] = matrix[row][col][2];
            }
        }
        return result;
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
        String fileName = this.imageName;
        writeToFile(fileName);

    }

    public void saveImageAs(String newName) {
        String extension = getExtensionFormat(format);
        String newImageName = newName + extension;
        writeToFile(newImageName);
    }

    private void writeToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
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
    public String getExtensionFormat(String format) {
        switch(format) {
            case "P3": return ".ppm";
            case "P2": return ".pgm";
            case "P1": return ".pbm";
            default: throw new IllegalArgumentException("Unknown format");
        }
    }

    public Image collageHorizontal(Image other, String newName) {

        if(!this.format.equals(other.format)) {
            System.out.println("Different formats!");
            return null;
        }

        if(this.width != other.width || this.height != other.height) {
            System.out.println("Different sizes!");
            return null;
        }

        if(this.format.equals("P1")) {
            Image result = new Image(
                    newName,
                    this.format,
                    this.width + other.width,
                    this.height,
                    new int[this.height][this.width + other.width]
            );

            for(int r = 0; r < height; r++) {
                // left image
                for(int c = 0; c < width; c++) {
                    result.pixels[r][c] = this.pixels[r][c];
                }

                // right image
                for(int c = 0; c < width; c++) {
                    result.pixels[r][c + width] = other.pixels[r][c];
                }
            }
            return result;
        }

        if(this.format.equals("P2")) {
            Image result = new Image(
                    newName,
                    this.format,
                    this.width + other.width,
                    this.height,
                    this.maxValue,
                    new int[this.height][this.width + other.width]
            );

            for(int r = 0; r < height; r++) {
                // left image
                for(int c = 0; c < width; c++) {
                    result.pixels[r][c] = this.pixels[r][c];
                }
                // right image
                for(int c = 0; c < width; c++) {
                    result.pixels[r][c + width] = other.pixels[r][c];
                }
            }
            return result;
        }

        if(format.equals("P3")) {
            Image result = new Image(
                    newName,
                    this.format,
                    this.width + other.width,
                    this.height,
                    this.maxValue,
                    new int[this.height][this.width + other.width][3]);
            for(int r = 0; r < height; r++) {
                // left image
                for(int c = 0; c < width; c++) {
                    for(int i = 0; i < 3; i++) {
                        result.colorPixels[r][c][i] = this.colorPixels[r][c][i];
                    }
                }

                // right image
                for(int c = 0; c < width; c++) {
                    for(int i = 0; i < 3; i++) {
                        result.colorPixels[r][c + width][i] = other.colorPixels[r][c][i];
                    }
                }
            }
            return result;
        }
        return null;
    }


    public Image collageVertical(Image other, String newName) {

        if(!this.format.equals(other.format)) {
            System.out.println("Different formats!");
            return null;
        }

        if(this.width != other.width || this.height != other.height) {
            System.out.println("Different sizes!");
            return null;
        }

        // PBM (P1)
        if(format.equals("P1")) {

            int[][] newPixels = new int[this.height + other.height][this.width];

            Image result = new Image(
                    newName,
                    this.format,
                    this.width,
                    this.height + other.height,
                    newPixels
            );

            // top image
            for(int r = 0; r < height; r++) {
                for(int c = 0; c < width; c++) {
                    result.getPixels()[r][c] = this.pixels[r][c];
                }
            }

            // bottom image
            for(int r = 0; r < height; r++) {
                for(int c = 0; c < width; c++) {
                    result.getPixels()[r + height][c] = other.pixels[r][c];
                }
            }

            return result;
        }

        // PGM (P2)
        if(format.equals("P2")) {

            int[][] newPixels = new int[this.height + other.height][this.width];

            Image result = new Image(
                    newName,
                    this.format,
                    this.width,
                    this.height + other.height,
                    this.maxValue,
                    newPixels
            );

            // top
            for(int r = 0; r < height; r++) {
                for(int c = 0; c < width; c++) {
                    result.getPixels()[r][c] = this.pixels[r][c];
                }
            }

            // bottom
            for(int r = 0; r < height; r++) {
                for(int c = 0; c < width; c++) {
                    result.getPixels()[r + height][c] = other.pixels[r][c];
                }
            }

            return result;
        }

        // PPM (P3)
        if(format.equals("P3")) {

            int[][][] newPixels = new int[this.height + other.height][this.width][3];

            Image result = new Image(
                    newName,
                    this.format,
                    this.width,
                    this.height + other.height,
                    this.maxValue,
                    newPixels
            );

            // top
            for(int r = 0; r < height; r++) {
                for(int c = 0; c < width; c++) {
                    for(int i = 0; i < 3; i++) {
                        result.getColorPixels()[r][c][i] = this.colorPixels[r][c][i];
                    }
                }
            }

            // bottom
            for(int r = 0; r < height; r++) {
                for(int c = 0; c < width; c++) {
                    for(int i = 0; i < 3; i++) {
                        result.getColorPixels()[r + height][c][i] = other.colorPixels[r][c][i];
                    }
                }
            }

            return result;
        }

        return null;
    }

}
