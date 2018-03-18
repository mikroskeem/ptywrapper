package eu.mikroskeem.ptywrapper;

import eu.mikroskeem.ptywrapper.internal.WinsizeWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * Window size object
 *
 * @author Mark Vainomaa
 */
public interface Winsize {
    /**
     * Gets window columns
     *
     * @return Window columns
     */
    int getCols();

    /**
     * Gets window rows
     *
     * @return Window rows
     */
    int getRows();

    /**
     * Creates new window size object
     *
     * @param cols Columns
     * @param rows Rows
     * @return Window size object
     */
    @NotNull
    static Winsize of(int cols, int rows) {
        return new WinsizeWrapper(cols, rows);
    }
}
