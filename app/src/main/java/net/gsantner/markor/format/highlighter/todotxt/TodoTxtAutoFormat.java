/*
 * Copyright (c) 2017 Gregor Santner and Markor contributors
 *
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */
package net.gsantner.markor.format.highlighter.todotxt;

import android.text.InputFilter;
import android.text.Spanned;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TodoTxtAutoFormat implements InputFilter {
    public static final SimpleDateFormat SDF_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);

    @Override
    public CharSequence filter(
            CharSequence source,
            int start,
            int end,
            Spanned dest,
            int dstart,
            int dend) {

        if (end - start == 1 &&
                start < source.length() &&
                dstart <= dest.length()) {
            char newChar = source.charAt(start);

            if (newChar == '\n') {
                return autoIndent(
                        source,
                        dest,
                        dstart,
                        dend);
            }
        }

        return source;
    }

    private CharSequence autoIndent(CharSequence source, Spanned dest, int dstart, int dend) {
        int istart = findLineBreakPosition(dest, dstart);

        // append white space of previous line and new indent
        return source + createIndentForNextLine(dest, dend, istart);
    }

    private int findLineBreakPosition(Spanned dest, int dstart) {
        int istart = dstart - 1;

        for (; istart > -1; --istart) {
            char c = dest.charAt(istart);

            if (c == '\n')
                break;
        }
        return istart;
    }

    private String createIndentForNextLine(Spanned dest, int dend, int istart) {
        return SDF_YYYY_MM_DD.format(new Date()) + " ";
    }
}
