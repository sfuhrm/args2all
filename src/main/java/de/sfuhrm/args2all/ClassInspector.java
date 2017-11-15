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

package de.sfuhrm.args2all;

import de.sfuhrm.args2all.mapping.Mapper;
import de.sfuhrm.args2all.mapping.Mapping;
import de.sfuhrm.args2all.model.ModelBase;
import de.sfuhrm.args2all.model.ModelParameter;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;

/** Converts command line parser specific POJO classes to the
 * generic model format.
 * @see ModelBase
 * */
public final class ClassInspector {

    /** The mapper from annotation classes to {@link ModelParameter}. */
    private Mapper mapper;

    /** Constructs a new class inspector. */
    public ClassInspector() {
        mapper = new Mapper();
    }

    /** Inspect the given class and return the model for it.
     * @param clazz the class to inspect for annotations.
     * @return the model derived from the class inspection.
     * */
    public ModelBase inspect(final Class<?> clazz) {
        ModelBase result = new ModelBase();
        result.setReference(clazz);
        Arrays.asList(clazz.getDeclaredFields())
                .stream()
                .forEach(
                    f -> {
                        for (Annotation annotation
                                : f.getDeclaredAnnotations()) {
                            ModelParameter parameter = null;

                            Mapping mapping = mapper.getClassMappings()
                                    .get(annotation.annotationType());
                            if (mapping != null) {
                                parameter =
                                        new ModelParameter(f);
                                parameter.setValueClass(f.getType());
                                parameter = mapping.createFrom(
                                        parameter, annotation);
                                result.getParameters().add(parameter);
                            }
                        }
                    }
                );

        Collections.sort(result.getParameters(), ModelBase.COMPARATOR);
        return result;
    }
}
