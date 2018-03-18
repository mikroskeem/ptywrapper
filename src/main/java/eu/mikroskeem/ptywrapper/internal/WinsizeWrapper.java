package eu.mikroskeem.ptywrapper.internal;

import eu.mikroskeem.ptywrapper.Winsize;

/**
 * @author Mark Vainomaa
 */
public final class WinsizeWrapper implements Winsize {
    private final int cols;
    private final int rows;

    public WinsizeWrapper(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getCols() {
        return cols;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Winsize) {
            return ((Winsize) o).getCols() == cols &&
                    ((Winsize) o).getRows() == rows;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Winsize(cols=" + cols + ", rows=" + rows + ")";
    }
}
