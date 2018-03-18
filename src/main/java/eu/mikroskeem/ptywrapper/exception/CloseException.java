package eu.mikroskeem.ptywrapper.exception;

/**
 * Thrown when close(2) fails
 *
 * @author Mark Vainomaa
 */
public class CloseException extends RuntimeException {
    public CloseException(int result) {
        super("Failed to close(). Result: " + result);
    }
}
