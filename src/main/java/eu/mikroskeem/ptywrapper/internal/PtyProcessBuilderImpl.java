package eu.mikroskeem.ptywrapper.internal;

import eu.mikroskeem.ptywrapper.PtyProcess;
import eu.mikroskeem.ptywrapper.PtyProcessBuilder;
import eu.mikroskeem.ptywrapper.PtyProcessHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Mark Vainomaa
 */
public final class PtyProcessBuilderImpl implements PtyProcessBuilder {
    public final static Path DEFAULT_PATH = Paths.get("/");

    private final List<String> args;
    private final Map<String, String> env;
    private Path workingDirectory;
    private PtyProcessHandler processHandler;

    public PtyProcessBuilderImpl(@NotNull List<String> args, @NotNull Map<String, String> env, boolean inheritParentEnvironment) {
        Map<String, String> envMap = new TreeMap<>();
        if(inheritParentEnvironment)
            envMap.putAll(env);
        envMap.putAll(env);

        this.args = Collections.unmodifiableList(args);
        this.env = Collections.unmodifiableMap(envMap);
        this.workingDirectory = DEFAULT_PATH;
    }

    @NotNull
    @Override
    public List<String> getCommand() {
        return this.args;
    }

    @NotNull
    @Override
    public Map<String, String> getEnvironment() {
        return this.env;
    }

    @NotNull
    @Override
    public Path getWorkingDirectory() {
        return this.workingDirectory;
    }

    @Override
    public void setWorkingDirectory(@NotNull Path workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    @Nullable
    @Override
    public PtyProcessHandler getProcessHandler() {
        return this.processHandler;
    }

    @Override
    public void setProcessHandler(@NotNull PtyProcessHandler processHandler) {
        this.processHandler = processHandler;
    }

    @NotNull
    @Override
    public PtyProcess start() {
        return null;
    }
}
