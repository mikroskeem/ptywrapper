package eu.mikroskeem.ptywrapper.exception;

/**
 * Thrown when ioctl(2) fails
 *
 * @author Mark Vainomaa
 */
public class IoctlException extends RuntimeException {
    public IoctlException(int result) {
        super("Failed ioctl(). Result: " + result);
    }
}
