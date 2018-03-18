package eu.mikroskeem.ptywrapper;

import eu.mikroskeem.ptywrapper.internal.PtyProcessBuilderImpl;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Process (in a pseudoterminal) wrapper. Implementation is guranteed to be compatible with {@link Process} class
 *
 * @author Mark Vainomaa
 */
public interface PtyProcess {
    void destroy();

    int exitValue() throws IllegalThreadStateException;

    @NotNull
    default InputStream getErrorStream() {
        throw new UnsupportedOperationException("Pseudoterminal does not support error stream!");
    }

    @NotNull
    InputStream getInputStream();

    @NotNull
    OutputStream getOutputStream();

    int waitFor() throws InterruptedException;

    @NotNull
    static PtyProcessBuilder builder(@NotNull List<String> argv) {
        return builder(argv, Collections.emptyMap());
    }

    @NotNull
    static PtyProcessBuilder builder(@NotNull List<String> argv, @NotNull Map<String, String> environment) {
        return builder(argv, environment, true);
    }

    @NotNull
    static PtyProcessBuilder builder(@NotNull List<String> argv, @NotNull Map<String, String> environment,
                                     boolean inheritParentEnvironment) {
        return new PtyProcessBuilderImpl(argv, environment, inheritParentEnvironment);
    }
}
