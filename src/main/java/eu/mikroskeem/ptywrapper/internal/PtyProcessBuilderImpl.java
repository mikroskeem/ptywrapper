/*
 * Copyright 2018 Mark Vainomaa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package eu.mikroskeem.ptywrapper.internal;

import eu.mikroskeem.ptywrapper.PtyProcess;
import eu.mikroskeem.ptywrapper.PtyProcessBuilder;
import eu.mikroskeem.ptywrapper.PtyProcessHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

        this.args = Collections.unmodifiableList(new ArrayList<>(args));
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
