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

package eu.mikroskeem.ptywrapper;

import eu.mikroskeem.ptywrapper.internal.PtyProcessBuilderImpl;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Process (in a pseudoterminal) wrapper.
 *
 * @author Mark Vainomaa
 */
public interface PtyProcess {
    /**
     * Gets command running in a pseudoterminal
     *
     * @return Command running in a pseudoterminal
     */
    @NotNull
    List<String> getCommand();

    /**
     * Gets pseudoterminal process environment variables
     *
     * @return Pseudoterminal process environment variables
     */
    @NotNull
    Map<String, String> getEnvironment();

    /**
     * Gets pseudoterminal wrapper
     *
     * @return Pseudoterminal wrapper
     */
    @NotNull
    Pty getPty();

    /**
     * Gets pseudoterminal process handler
     *
     * @return Pseudoterminal process handler
     */
    @NotNull
    PtyProcessHandler getProcessHandler();

    /**
     * Pseudoterminal process handler
     *
     * @param processHandler Pseudoterminal process handler
     */
    void setProcessHandler(@NotNull PtyProcessHandler processHandler);

    /**
     * Returns whether process is running or not
     *
     * @return Whether process is running or not
     */
    boolean isRunning();

    /**
     * Gets process id
     *
     * @return Process id
     */
    int getPid();

    /**
     * Gets process exit code
     *
     * @return Process exit code, or {@link Integer#MIN_VALUE} if
     */
    int getExitCode();

    /**
     * Asks provided {@link PtyProcessHandler} to write data to pseudoterminal
     */
    void askStdin();

    /**
     * Writes to pseudoterminal directly
     *
     * @param bufferSupplier {@link ByteBuffer} supply
     */
    void writeStdin(@NotNull Supplier<@NotNull ByteBuffer> bufferSupplier);

    /**
     * Closes stdin
     */
    void closeStdin();

    /**
     * Builds new pseudoterminal process.
     *
     * Will inherit parent's environment variables.
     *
     * @param argv Process command line
     * @return Instance of {@link PtyProcessBuilder}
     */
    @NotNull
    static PtyProcessBuilder builder(@NotNull List<String> argv) {
        return builder(argv, Collections.emptyMap());
    }

    /**
     * Builds new pseudoterminal process
     *
     * Will inherit parent's environment variables and specified ones override them.
     *
     * @param argv Process command line
     * @param environment Environment variables
     * @return Instance of {@link PtyProcessBuilder}
     */
    @NotNull
    static PtyProcessBuilder builder(@NotNull List<String> argv, @NotNull Map<String, String> environment) {
        return builder(argv, environment, true);
    }

    /**
     * Builds new pseudoterminal process
     *
     * @param argv                      Process command line
     * @param environment               Process environment variables.
     *                                   Note: variables defined in this map override inherited variables
     * @param inheritParentEnvironment  Whether process should inherit parent's environment variables or not
     * @return Instance of {@link PtyProcessBuilder}
     */
    @NotNull
    static PtyProcessBuilder builder(@NotNull List<String> argv, @NotNull Map<String, String> environment,
                                     boolean inheritParentEnvironment) {
        return new PtyProcessBuilderImpl(argv, environment, inheritParentEnvironment);
    }
}
