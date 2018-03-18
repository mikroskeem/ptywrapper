package eu.mikroskeem.ptywrapper.internal.util;

/**
 * SneakyThrows! Source: https://github.com/fge/throwing-lambdas/issues/9
 *
 * @author Mark Vainomaa
 */
public class SneakyThrows {
    @SuppressWarnings("unchecked")
    static <T extends Throwable> void sneakyThrow(Throwable t) throws T {
        throw (T) t;
    }
}
