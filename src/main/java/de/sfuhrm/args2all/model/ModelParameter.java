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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** A parameter associated to a field. */
@EqualsAndHashCode()
@ToString
public class ModelParameter {
    /** The field this parameter belongs to.
     * TBD should be the field name instead?
     * */
    @Getter
    @Setter
    private Field reference;

    /** The names in the command line for this field. */
    @Getter @Setter
    private List<String> names;

    /** The human readable description in the help text for this parameter. */
    @Getter @Setter
    private String description;

    /** Is this parameter required? */
    @Getter @Setter
    private boolean required;

    /** What is the name of the value type? */
    @Getter @Setter
    private String valueName;

    /** The value class for this parameter. */
    @Getter @Setter
    private Class<?> valueClass;

    /** The key for sorting the parameter. */
    @Getter @Setter
    private int order = -1;

    /** Creates a new instance of the parameter.
     * @param myReference the field this parameter belongs to.
     * */
    public ModelParameter(final Field myReference) {
        this.reference = Objects.requireNonNull(myReference);
        this.names = new ArrayList<>();
    }
}
