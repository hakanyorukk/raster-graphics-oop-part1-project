package exceptions;

public class InvalidImageName extends RuntimeException {
    public InvalidImageName(String message) {
        super(message);
    }
}
