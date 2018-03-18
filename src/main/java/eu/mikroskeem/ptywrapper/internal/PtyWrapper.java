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
import eu.mikroskeem.ptywrapper.internal.natives.CLibrary;
import eu.mikroskeem.ptywrapper.internal.natives.CUtil;
import eu.mikroskeem.ptywrapper.internal.struct.Termios;
import eu.mikroskeem.ptywrapper.internal.struct.Winsize;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;

/**
 * @author Mark Vainomaa
 */
public final class PtyWrapper implements Pty, Closeable {
    private final int masterFd;
    private final int slaveFd;
    private final String ptyPath;

    public PtyWrapper() {
        // Set up pointers
        IntByReference amaster = new IntByReference();
        IntByReference aslave = new IntByReference();
        Memory memory = new Memory(255);

        // Set up termios & winsize
        Termios.ByReference termios = new Termios.ByReference();
        Winsize.ByReference winsize = new Winsize.ByReference();

        winsize.ws_col = 80;
        winsize.ws_row = 24;

        // Initializes a new PTY
        int r = CUtil.INSTANCE.openpty(amaster, aslave, memory, termios, winsize);
        // TODO: throw exception if openpty failed

        this.masterFd = amaster.getValue();
        this.slaveFd = aslave.getValue();
        this.ptyPath = memory.getString(0, "UTF-8");

        // Set window size
        CLibrary.INSTANCE.ioctl(this.masterFd, CLibrary.TIOCSWINSZ, winsize);
    }

    @Override
    @NotNull
    public String getPath() {
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

    private Winsize.ByReference getWinSize() {
        Winsize.ByReference winsize = new Winsize.ByReference();
        CLibrary.INSTANCE.ioctl(this.masterFd, CLibrary.TIOCGWINSZ, winsize);
        return winsize;
    }

    @Override
    public int getRows() {
        return getWinSize().ws_row;
    }

    @Override
    public int getCols() {
        return getWinSize().ws_col;
    }

    @Override
    public void close() {
        CLibrary.INSTANCE.close(masterFd);
        CLibrary.INSTANCE.close(slaveFd);
    }
}
