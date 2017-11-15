/*
 * Copyright (C) 2017 Stephan Fuhrmann
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package de.sfuhrm.args2all.mapping;

import de.sfuhrm.args2all.model.ModelParameter;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.util.Objects;

/** Maps one single annotation class to a {@linkplain ModelParameter}.
 * @param <U> the annotation that is being mapped by this class.
 * @author Stephan Fuhrmann
 * */
public abstract class Mapping<U extends Annotation> {

    /** The annotation class to match. */
    @Getter
    private final Class<U> annotationClass;

    /** Creates a new instance.
     * @param clazz the annotation class to match.
     * */
    Mapping(final Class<U> clazz) {
        annotationClass = Objects.requireNonNull(clazz);
    }

    /** Creates a model parameter from an field annotation.
     * @param modelParameter the model parameter to write the data to.
     * @param annotation the fields annotation to fill the
     *                   model with.
     * @return the created model parameter object.
     * */
    public abstract ModelParameter createFrom(
            ModelParameter modelParameter, U annotation);

    /** Maps the empty String to {@code null}.
     * @param in the String to map.
     * @return {@code in} or {@code null} if {@code in} was the empty string.
     * */
    static String emptyIsNull(final String in) {
        if (in == null) {
            return null;
        }
        if (in.equals("")) {
            return null;
        }
        return in;
    }
}
