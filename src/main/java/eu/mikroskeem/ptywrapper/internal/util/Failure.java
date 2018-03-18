package eu.mikroskeem.ptywrapper.internal.util;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Mark Vainomaa
 */
public final class Failure {
    public static void checkThrows(int result, Supplier<Throwable> throwableSupplier) {
        if(result < 0)
            SneakyThrows.sneakyThrow(throwableSupplier.get());
    }

    public static void checkThrows(int result, Function<Integer, Throwable> throwableSupplier) {
        if(result < 0)
            SneakyThrows.sneakyThrow(throwableSupplier.apply(result));
    }
}
