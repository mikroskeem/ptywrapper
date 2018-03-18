package eu.mikroskeem.ptywrapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Pseudoterminal process builder
 *
 * @author Mark Vainomaa
 */
public interface PtyProcessBuilder {
    @NotNull
    List<String> getCommand();

    @NotNull
    Map<String, String> getEnvironment();

    @NotNull
    Path getWorkingDirectory();

    void setWorkingDirectory(@NotNull Path workingDirectory);

    @Nullable
    PtyProcessHandler getProcessHandler();

    void setProcessHandler(@NotNull PtyProcessHandler processHandler);

    @NotNull
    PtyProcess start();
}
