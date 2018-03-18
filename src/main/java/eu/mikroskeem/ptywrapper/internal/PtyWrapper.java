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

import com.sun.jna.Memory;
import com.sun.jna.ptr.IntByReference;
import eu.mikroskeem.ptywrapper.Pty;
import eu.mikroskeem.ptywrapper.Winsize;
import eu.mikroskeem.ptywrapper.exception.CloseException;
import eu.mikroskeem.ptywrapper.exception.IoctlException;
import eu.mikroskeem.ptywrapper.exception.PtyOpenException;
import eu.mikroskeem.ptywrapper.internal.natives.CLibrary;
import eu.mikroskeem.ptywrapper.internal.natives.CUtil;
import eu.mikroskeem.ptywrapper.internal.struct.TermiosStruct;
import eu.mikroskeem.ptywrapper.internal.struct.WinsizeStruct;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.nio.file.Path;
import java.nio.file.Paths;

import static eu.mikroskeem.ptywrapper.internal.util.Failure.checkThrows;

/**
 * Pty wrapper, doing all heavy lifting
 *
 * @author Mark Vainomaa
 */
public final class PtyWrapper implements Pty, Closeable {
    private final int masterFd;
    private final int slaveFd;
    private final Path ptyPath;

    public PtyWrapper(@NotNull Winsize winsize) {
        // Set up pointers
        IntByReference amaster = new IntByReference();
        IntByReference aslave = new IntByReference();
        Memory memory = new Memory(255);

        // Set up termios & winsize
        TermiosStruct.ByReference termiosStruct = new TermiosStruct.ByReference();
        WinsizeStruct.ByReference winsizeStruct = new WinsizeStruct.ByReference();

        // Copy cols & rows
        winsizeStruct.ws_col = (short) winsize.getCols();
        winsizeStruct.ws_row = (short) winsize.getRows();

        // Initializes a new PTY
        checkThrows(CUtil.INSTANCE.openpty(amaster, aslave, memory, termiosStruct, winsizeStruct), PtyOpenException::new);

        this.masterFd = amaster.getValue();
        this.slaveFd = aslave.getValue();
        this.ptyPath = Paths.get(memory.getString(0, "UTF-8"));
    }

    public PtyWrapper() {
        this(Winsize.of(80, 24));
    }

    @Override
    @NotNull
    public Path getPath() {
        return ptyPath;
    }

    @Override
    public int getMasterFd() {
        return masterFd;
    }

    @Override
    public int getSlaveFd() {
        return slaveFd;
    }

    @NotNull
    @Override
    public Winsize getWinsize() {
        WinsizeStruct.ByReference winsize = new WinsizeStruct.ByReference();
        checkThrows(CLibrary.INSTANCE.ioctl(this.masterFd, CLibrary.TIOCGWINSZ, winsize), IoctlException::new);
        return new WinsizeWrapper(winsize.ws_col, winsize.ws_row);
    }

    @Override
    public void setWinsize(@NotNull Winsize winsize) {
        WinsizeStruct.ByReference winsizeStruct = new WinsizeStruct.ByReference();
        winsizeStruct.ws_row = (short) winsize.getRows();
        winsizeStruct.ws_col = (short) winsize.getCols();
        checkThrows(CLibrary.INSTANCE.ioctl(this.masterFd, CLibrary.TIOCSWINSZ, winsizeStruct), IoctlException::new);
    }

    @Override
    public void close() {
        checkThrows(CLibrary.INSTANCE.close(masterFd), CloseException::new);
        checkThrows(CLibrary.INSTANCE.close(slaveFd), CloseException::new);
    }
}
