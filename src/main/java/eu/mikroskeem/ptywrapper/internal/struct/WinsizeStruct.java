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

package eu.mikroskeem.ptywrapper.internal.struct;

import com.sun.jna.Structure;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Winsize struct
 *
 * @author Mark Vainomaa
 */
public class WinsizeStruct extends Structure {
    public static class ByReference extends WinsizeStruct implements Structure.ByReference {}

    @FieldFromStruct public short ws_row;
    @FieldFromStruct public short ws_col;
    @FieldFromStruct public short ws_xpixel;
    @FieldFromStruct public short ws_ypixel;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.stream(this.getClass().getFields())
                .filter(f -> f.getAnnotation(FieldFromStruct.class) != null)
                .map(Field::getName)
                .collect(Collectors.toList());
    }
}
