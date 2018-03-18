package eu.mikroskeem.ptywrapper;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

/**
 * Pseudoterminal handler
 *
 * @author Mark Vainomaa
 */
public interface PtyProcessHandler {
    default void onPreStart(@NotNull PtyProcess ptyProcess) {}

    default void onStart(@NotNull PtyProcess ptyProcess) {}

    default void onExit(int exitCode) {}

    default void onStdout(ByteBuffer buffer, boolean closed) {}

    boolean onStdinReady(ByteBuffer buffer);
}
