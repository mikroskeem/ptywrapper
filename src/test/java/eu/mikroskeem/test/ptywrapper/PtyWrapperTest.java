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

package eu.mikroskeem.test.ptywrapper;

import com.sun.jna.Memory;
import com.sun.jna.ptr.IntByReference;
import eu.mikroskeem.ptywrapper.Pty;
import eu.mikroskeem.ptywrapper.Winsize;
import eu.mikroskeem.ptywrapper.internal.Errno;
import eu.mikroskeem.ptywrapper.internal.natives.CLibrary;
import eu.mikroskeem.ptywrapper.internal.natives.CUtil;
import eu.mikroskeem.ptywrapper.internal.struct.WinsizeStruct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Mark Vainomaa
 */
public class PtyWrapperTest {
    @Test
    public void testErrno() throws Exception {
        Assertions.assertNotNull(Errno.getStringError());
    }

    @Test
    public void testLibraryHooking() throws Exception {
        IntByReference amaster = new IntByReference();
        IntByReference aslave = new IntByReference();
        Memory memory = new Memory(255);

        int r = CUtil.INSTANCE.openpty(amaster, aslave, memory, null, null);

        // Check if pty opening succeeded
        Assertions.assertEquals(0, r);
        Assertions.assertTrue(amaster.getValue() >= 0);
        Assertions.assertTrue(aslave.getValue() >= 0);
        Assertions.assertNotNull(memory.getString(0, "UTF-8"));

        // Get window size
        WinsizeStruct.ByReference winsize = new WinsizeStruct.ByReference();
        r = CLibrary.INSTANCE.ioctl(amaster.getValue(), CLibrary.TIOCGWINSZ, winsize);
        Assertions.assertEquals(0, r);

        Assertions.assertEquals(0, winsize.ws_col);
        Assertions.assertEquals(0, winsize.ws_row);

        // Make sure to close fds
        Assertions.assertTrue(CLibrary.INSTANCE.close(amaster.getValue()) >= 0);
        Assertions.assertTrue(CLibrary.INSTANCE.close(aslave.getValue()) >= 0);
    }

    @Test
    public void testDefaultWrapperWinsize() throws Exception {
        try(Pty pty = Pty.createNew()) {
            Winsize winsize = pty.getWinsize();
            Assertions.assertEquals(80, winsize.getCols());
            Assertions.assertEquals(24, winsize.getRows());

            Winsize newWinsize = Winsize.of(90, 30);
            pty.setWinsize(newWinsize);
            Assertions.assertEquals(newWinsize, pty.getWinsize());
        }
    }
}
