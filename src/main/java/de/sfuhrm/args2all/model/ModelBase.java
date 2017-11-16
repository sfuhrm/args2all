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

package de.sfuhrm.args2all.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/** Base for a class that has annotations from a command line parser.
 * @author Stephan Fuhrmann
 * */
@EqualsAndHashCode()
@ToString
public final class ModelBase {

    /** Which class is this model for? */
    @Getter @Setter
    private Class<?> reference;

    /** The names in the command line for this field. */
    @Getter @Setter
    private List<String> names;

    /** The parameters for the referenced entity. */
    @Getter @Setter
    private List<ModelParameter> parameters;

    /** Construct a new instance. */
    public ModelBase() {
        parameters = new ArrayList<>();
    }

    /** Does this model have a nameless parameter?
     * Example for a nameless parameter: {@code ls -al foo bar} has
     * two nameless parameters, {@code foo} and {@code bar}.
     * @return true if the model has a nameless parameter.
     * */
    public boolean hasNameless() {
        return parameters.stream()
                .anyMatch(p -> p.getClass().equals(ModelParameter.class));
    }

    /** Get the nameless parameter.
     * Example for a nameless parameter: {@code ls -al foo bar} has
     * two nameless parameters, {@code foo} and {@code bar}.
     * @return the optional nameless parameter.
     * */
    public List<ModelParameter> getNameless() {
        return parameters.stream()
                .filter(p -> p.getNames().isEmpty())
                .collect(Collectors.toList());
    }

    /** Get the namefull parameters.
     * Example for a nameless parameter: {@code ls -al foo bar} has
     * two nameless parameters, {@code foo} and {@code bar}.
     * @return the namefull parameters.
     * */
    public List<ModelParameter> getNamefull() {
        return parameters.stream()
                .filter(p -> !p.getNames().isEmpty())
                .collect(Collectors.toList());
    }

    /** The comparator used to order the named model parameters.
     * Uses first the {@linkplain ModelParameter#getOrder() order}
     * and then the {@linkplain ModelParameter#getNames() first name}
     * as sort keys.
     * */
    public static final Comparator<ModelParameter> COMPARATOR =
            Comparator.comparingInt(ModelParameter::getOrder)
                    .thenComparing(q ->
                            { if (q.getNames().isEmpty()) {
                                return "";
                                } else {
                                    return q.getNames().get(0);
                                }
                            },
                            String.CASE_INSENSITIVE_ORDER);
}
