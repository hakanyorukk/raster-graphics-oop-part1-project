package transformations;

import image.Image;

public class Rotate implements Transformation{
    private String direction;

    public Rotate(String direction) {
        this.direction = direction;
    }

    @Override
    public void apply(Image image) {
       image.rotate(direction);
    }

    @Override
    public String getName() {
        return "rotate " + direction;
    }

    @Override
    public String toString() {
        return "Rotate, direction: " + direction;
    }

    // 1. +90  => Transpose   -> ReverseRows

    // 2. -90  => Transpose   -> ReverseCols  |   ReverseCols -> Transpose

    // 3. +180 => ReverseRows -> ReverseCols  |  +90 * 2

    // 4. -180 => ReverseCols -> ReverseRows  |  -90 * 2


    // transpose => Flip matrix[i][j] -> matrix[j][i]


    // reverseRows =>  1 2 3 -> 3 2 1


    //                 1       3
    //   reverseCols   2   ->  2
    //                 3       1
}
