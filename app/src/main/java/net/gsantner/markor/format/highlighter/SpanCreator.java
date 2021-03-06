/*
 * Copyright (c) 2014 Jeff Martin
 * Copyright (c) 2015 Pedro Lafuente
 * Copyright (c) 2017 Gregor Santner and Markor contributors
 *
 * Licensed under the MIT license. See LICENSE file in the project root for details.
 */
package net.gsantner.markor.format.highlighter;

import android.text.ParcelableSpan;

import java.util.regex.Matcher;

public interface SpanCreator {
    ParcelableSpan create(Matcher matcher, int iM);
}
