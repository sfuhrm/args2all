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
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/** Converts command line parser specific POJO classes to the
 * generic model format.
 * @author Stephan Fuhrmann
 * @see ModelBase
 * */
@Slf4j
final class ClassInspector {

    /** The mapper from annotation classes to {@link ModelParameter}. */
    private final Mapper mapper;

    /** Constructs a new class inspector. */
    ClassInspector() {
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
                .forEach(
                    f -> {
                        log.debug("Processing field {}", f.getName());
                        processField(result, f);
                    }
                );

        Arrays.asList(clazz.getDeclaredMethods())
                .forEach(
                        m -> {
                            log.debug("Processing method {}", m.getName());
                            processMethod(result, m);
                        }
                );

        result.getParameters().sort(ModelBase.COMPARATOR);
        return result;
    }

    /** Processes a method and adds the results to the
     * model.
     * @param result the model to eventually add to.
     * @param m the method to extract data from.
     * */
    private void processMethod(final ModelBase result,
                               final Method m) {
        for (Annotation annotation
                : m.getDeclaredAnnotations()) {
            ModelParameter parameter;

            Mapping<Annotation> mapping = mapper.getClassMappings()
                    .get(annotation.annotationType());
            if (mapping != null) {
                parameter =
                        new ModelParameter(m);
                int params = m.getParameterCount();
                if (params >= 1) {
                    parameter.setValueClass(
                            m.getParameterTypes()[0]);
                }
                parameter = mapping.createFrom(
                        parameter, annotation);
                result.getParameters().add(parameter);
            }
        }
    }

    /** Processes a field and adds the results to the
     * model.
     * @param result the model to eventually add to.
     * @param f the field to extract data from.
     * */
    private void processField(final ModelBase result,
                              final Field f) {
        for (Annotation annotation
                : f.getDeclaredAnnotations()) {
            ModelParameter parameter;

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
}
