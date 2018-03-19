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

import eu.mikroskeem.ptywrapper.Pty;
import eu.mikroskeem.ptywrapper.PtyProcess;
import eu.mikroskeem.ptywrapper.PtyProcessHandler;
import eu.mikroskeem.ptywrapper.exception.StdinClosedException;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * @author Mark Vainomaa
 */
public class PtyProcessImpl implements PtyProcess {
    private final List<String> command;
    private final Map<String, String> environment;
    private final Pty pty;
    private final AtomicReference<PtyProcessHandler> processHandler = new AtomicReference<>();

    private final AtomicBoolean stdinClosed = new AtomicBoolean(false);

    public PtyProcessImpl(List<String> command, Map<String, String> environment, Pty pty) {
        this.command = command;
        this.environment = environment;
        this.pty = pty;
    }

    @NotNull
    @Override
    public List<String> getCommand() {
        return command;
    }

    @NotNull
    @Override
    public Map<String, String> getEnvironment() {
        return environment;
    }

    @NotNull
    @Override
    public Pty getPty() {
        return pty;
    }

    @NotNull
    @Override
    public PtyProcessHandler getProcessHandler() {
        return processHandler.get();
    }

    @Override
    public void setProcessHandler(@NotNull PtyProcessHandler processHandler) {
        this.processHandler.set(processHandler);
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPid() {
        return 0;
    }

    @Override
    public int getExitCode() {
        return 0;
    }

    @Override
    public void askStdin() {
        if(stdinClosed.get())
            throw new StdinClosedException();

    }

    @Override
    public void writeStdin(@NotNull Supplier<@NotNull ByteBuffer> bufferSupplier) {
        if(stdinClosed.get())
            throw new StdinClosedException();

    }

    @Override
    public void closeStdin() {
        if(!stdinClosed.compareAndSet(false, true)) {
            throw new StdinClosedException();
        }
    }
}