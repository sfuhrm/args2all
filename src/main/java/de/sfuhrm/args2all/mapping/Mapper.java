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

import lombok.Getter;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Manages the annotation mappings that are available.
 * The mappings can be checked with the {@link #getClassMappings()} call.
 * @see Mapping
 * @author Stephan Fuhrmann
 * */
public class Mapper {

    /** Associative map with the key being the
     * annotation class and the value being the mapping
     * itself.
     * */
    @Getter
    private final Map<Class<? extends Annotation>, Mapping> classMappings;

    /** Creates a new mapper instance with the default mappings
     * installed.
     * */
    public Mapper() {
        List<Mapping> mappings = Arrays.asList(
                new Args4jArgumentMapping(),
                new Args4jOptionMapping(),
                new JCommanderParameterMapping(),
                new PicoCliOptionMapping(),
                new PicoCliParametersMapping()
        );

        Map<Class<? extends Annotation>, Mapping> map = mappings
                .stream()
                .collect(Collectors.toMap(
                        p -> p.getAnnotationClass(), p -> p));
        classMappings = Collections.unmodifiableMap(map);
    }
}
