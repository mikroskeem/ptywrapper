package eu.mikroskeem.ptywrapper.exception;

/**
 * Pseudoterminal open exception. Thrown when pseudoterminal creating fails
 *
 * @author Mark Vainomaa
 */
public final class PtyOpenException extends RuntimeException {
    public PtyOpenException(int resultCode) {
        super("Failed to open Pty. Result code: " + resultCode);
    }
}
