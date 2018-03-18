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
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import eu.mikroskeem.ptywrapper.internal.natives.CLibrary;
import org.jetbrains.annotations.NotNull;

/**
 * Global errno variable reader and strerror wrapper
 *
 * @author Mark Vainomaa
 */
public final class Errno {
    private static IntByReference errno;

    public static synchronized IntByReference getErrnoReference() {
        if(errno == null) {
            errno = new IntByReference();
            errno.setPointer(NativeLibrary.getInstance("c").getGlobalVariableAddress("errno"));
        }

        return errno;
    }

    public static synchronized int getErrno() {
        return getErrnoReference().getValue();
    }

    @NotNull
    // Note: strerror_r does not return NULL, correct me if I'm wrong
    public static synchronized String getStringError() {
        int errorCode = getErrno();
        Memory buf = new Memory(1024);
        Pointer errstring = CLibrary.INSTANCE.strerror_r(errorCode, buf, (int)buf.size());
        return errstring.getString(0);
    }
}
