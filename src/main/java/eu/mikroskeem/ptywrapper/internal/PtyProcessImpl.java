package eu.mikroskeem.ptywrapper.internal;

import eu.mikroskeem.ptywrapper.PtyProcess;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Mark Vainomaa
 */
public class PtyProcessImpl extends Process implements PtyProcess {
    @Override
    public OutputStream getOutputStream() {
        return null;
    }

    @Override
    public InputStream getInputStream() {
        return null;
    }

    @Override
    public InputStream getErrorStream() {
        return null;
    }

    @Override
    public int waitFor() throws InterruptedException {
        return 0;
    }

    @Override
    public int exitValue() {
        return 0;
    }

    @Override
    public void destroy() {

    }
}
