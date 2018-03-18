package eu.mikroskeem.ptywrapper.internal.util;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Like Guava's Preconditions, but for C return codes
 *
 * @author Mark Vainomaa
 */
public final class Failure {
    public static void checkThrows(int result, @NotNull Supplier<Throwable> throwableSupplier) {
        if(result < 0)
            SneakyThrows.sneakyThrow(throwableSupplier.get());
    }

    public static void checkThrows(int result, @NotNull Function<Integer, Throwable> throwableSupplier) {
        if(result < 0)
            SneakyThrows.sneakyThrow(throwableSupplier.apply(result));
    }
}
