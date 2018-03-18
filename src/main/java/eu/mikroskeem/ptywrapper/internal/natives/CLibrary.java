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

package eu.mikroskeem.ptywrapper.internal.natives;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * @author Mark Vainomaa
 */
@SuppressWarnings("OctalInteger")
public interface CLibrary extends Library {
    CLibrary INSTANCE = Native.loadLibrary("c", CLibrary.class);

    /**
     * https://linux.die.net/man/2/close
     */
    int close(int fd);

    /**
     * See https://linux.die.net/man/2/ioctl
     */
    int ioctl(int fd, int request, Object... args);

    // ioctl(2) requests
    int TIOCGWINSZ = 0x5413;
    int TIOCSWINSZ = 0x5414;

    /**
     * See https://linux.die.net/man/2/fcntl
     */
    int fcntl(int fd, int cmd, Object... args);

    // fcntl(2) requests
    int F_GETFL = 3;
    int F_SETFL = 4;
    int O_NONBLOCK = 04000;
}
