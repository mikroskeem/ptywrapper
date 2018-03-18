package eu.mikroskeem.ptywrapper.internal.util;

/**
 * @author Mark Vainomaa
 */
public class SneakyThrows {
    @SuppressWarnings("unchecked")
    static <T extends Throwable> void sneakyThrow(Throwable t) throws T {
        throw (T) t;
    }
}
