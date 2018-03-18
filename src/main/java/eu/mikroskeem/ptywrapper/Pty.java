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

import eu.mikroskeem.ptywrapper.internal.PtyWrapper;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

/**
 * Pseudoterminal object
 *
 * @author Mark Vainomaa
 */
public interface Pty extends AutoCloseable {
    /**
     * Gets pseudoterminal file path
     *
     * @return Pseudoterminal file path
     */
    @NotNull
    Path getPath();

    /**
     * Gets pseudoterminal master file descriptor
     *
     * @return Pseudoterminal master file descriptor
     */
    int getMasterFd();

    /**
     * Gets pseudoterminal slave file descriptor
     *
     * @return Pseudoterminal slave file descriptor
     */
    int getSlaveFd();

    /**
     * Gets pseudoterminal window size
     *
     * @return Pseudoterminal window size
     */
    @NotNull
    Winsize getWinsize();

    /**
     * Sets pseudoterminal window size
     *
     * @param winsize Window size object
     */
    void setWinsize(@NotNull Winsize winsize);

    /**
     * Creates new pseudoterminal object
     *
     * @return Pseudoterminal object
     */
    @NotNull
    static Pty createNew() {
        return new PtyWrapper();
    }

    /**
     * Creates new pseudoterminal object with custom window size
     *
     * @return Pseudoterminal object
     */
    @NotNull
    static Pty createNew(@NotNull Winsize winsize) {
        return new PtyWrapper(winsize);
    }
}
